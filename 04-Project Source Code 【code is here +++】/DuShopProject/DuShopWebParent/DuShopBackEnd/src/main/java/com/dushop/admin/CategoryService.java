package com.dushop.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.dushop.common.entity.Category;
import com.dushop.common.entity.CategoryNotFoundException;

/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.admin.category
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-07-24  13:35
 *@Description: TODO
 *@Version: 1.0
 */

@Service
@Transactional
public class CategoryService {

    public static final int ROOT_CATEGORIES_PER_PAGE = 4;

    @Autowired
    private CategoryRepository repo;

    /*
     * @description: List the categories in hierachical way   Parent/--Subcategory/----Subcategory
     * @author: Jiang Chufeng
     * @date: 2022/7/24 17:10
     * @param:
     * @return: java.util.List<com.dushop.common.entity.Category>
     */
    /*self-code, but adapted from brand module*/
    public List<Category> listByPage(CategoryPageInfo pageInfo, int pageNum, String sortDir, String keyword) {
        Sort sort = Sort.by("name");

        if (sortDir.equals("asc")) {
            sort = sort.ascending();
        } else if (sortDir.equals("desc")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

        Page<Category> pageCategories = null;

        if (keyword != null && !keyword.isEmpty()) {
            pageCategories = repo.search(keyword, pageable);
        } else {
            pageCategories = repo.findRootCategories(pageable);
        }

        List<Category> rootCategories = pageCategories.getContent();
        /*****************************
         @Author: Code Java.
         “Spring Boot Tutorials Playlist” [online]
         Available at: https://youtu.be/zDc63OHY_v8
         ****************************/
        pageInfo.setTotalElements(pageCategories.getTotalElements());
        pageInfo.setTotalPages(pageCategories.getTotalPages());

        if (keyword != null && !keyword.isEmpty()) {
            List<Category> searchResult = pageCategories.getContent();
            for (Category category : searchResult) {
                category.setHasChildren(category.getChildren().size() > 0);
            }

            return searchResult;

        } else {
            return listHierarchicalCategories(rootCategories, sortDir);
        }
    }

    /*****************************
     @Author: Code Java.
     “Spring Boot Tutorials Playlist” [online]
     Available at: https://youtu.be/zDc63OHY_v8
     ****************************/
    private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);

            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));

                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
            }
        }

        return hierarchicalCategories;
    }
    /*****************************
     @Author: Code Java.
     “Spring Boot Tutorials Playlist” [online]
     Available at: https://youtu.be/zDc63OHY_v8
     ****************************/
    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,
                                               Category parent, int subLevel, String sortDir) {
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);
        int newSubLevel = subLevel + 1;

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            hierarchicalCategories.add(Category.copyFull(subCategory, name));

            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir);
        }

    }

    /*
     * @description: Category for product search
     * @author: Jiang Chufeng
     * @date: 2022/8/9 0:13
     * @param: category
     * @return: com.dushop.common.entity.Category
     */
    /*self-code, but adapted from brand module*/
    public Category save(Category category) {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentIDs() == null ? "-" : parent.getAllParentIDs();
            allParentIds += String.valueOf(parent.getId()) + "-";
            category.setAllParentIDs(allParentIds);
        }
        return repo.save(category);
    }

    /*
     * @description: Resuse from test / List wiithout hierachical
     * @author: Jiang Chufeng
     * @date: 2022/7/24 15:01
     * @param:
     * @return: java.util.List<com.dushop.common.entity.Category>
     */
    /*****************************
     @Author: Code Java.
     “Spring Boot Tutorials Playlist” [online]
     Available at: https://youtu.be/zDc63OHY_v8
     ****************************/
    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = repo.findRootCategories(Sort.by("name").ascending());

        for (Category category : categoriesInDB) {
            categoriesUsedInForm.add(Category.copyIdAndName(category));

            Set<Category> children = sortSubCategories(category.getChildren());

            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

                listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
            }
        }

        return categoriesUsedInForm;
    }

    /*****************************
     @Author: Code Java.
     “Spring Boot Tutorials Playlist” [online]
     Available at: https://youtu.be/zDc63OHY_v8
     ****************************/
    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm,
                                             Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren());

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

            listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }


    /*self-code*/
    public Category get(Integer id) throws CategoryNotFoundException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
    }
    /*self-code*/
    public String checkUnique(Integer id, String name, String alias) {
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = repo.findByName(name);

        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            } else {
                Category categoryByAlias = repo.findByAlias(alias);
                if (categoryByAlias != null) {
                    return "DuplicateAlias";
                }
            }
        } else {
            if (categoryByName != null && categoryByName.getId() != id) {
                return "DuplicateName";
            }

            Category categoryByAlias = repo.findByAlias(alias);
            if (categoryByAlias != null && categoryByAlias.getId() != id) {
                return "DuplicateAlias";
            }
        }
        return "OK";
    }

    /*****************************
     @Author: Code Java.
     “Spring Boot Tutorials Playlist” [online]
     Available at: https://youtu.be/zDc63OHY_v8
     ****************************/
    private SortedSet<Category> sortSubCategories(Set<Category> children) {
        return sortSubCategories(children, "asc");
    }

    /*****************************
     @Author: Code Java.
     “Spring Boot Tutorials Playlist” [online]
     Available at: https://youtu.be/zDc63OHY_v8
     ****************************/
    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category cat1, Category cat2) {
                if (sortDir.equals("asc")) {
                    return cat1.getName().compareTo(cat2.getName());
                } else {
                    return cat2.getName().compareTo(cat1.getName());
                }
            }
        });

        sortedChildren.addAll(children);

        return sortedChildren;
    }

    /*self-code*/
    public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    /*self-code*/
    public void delete(Integer id) throws CategoryNotFoundException {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
        repo.deleteById(id);
    }
}