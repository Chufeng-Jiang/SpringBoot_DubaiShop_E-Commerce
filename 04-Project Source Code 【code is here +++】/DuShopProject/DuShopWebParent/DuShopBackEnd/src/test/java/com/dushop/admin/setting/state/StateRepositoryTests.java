package com.dushop.admin.setting.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.dushop.admin.setting.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.dushop.common.entity.Country;
import com.dushop.common.entity.State;
/*
 *@BelongsProject: DuShopProject
 *@BelongsPackage: com.dushop.admin.setting.state
 *@Author: Jiang Chufeng
 *@CreateTime: 2022-08-14  22:07
 *@Description: TODO
 *@Version: 1.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {

    @Autowired private StateRepository repo;
    @Autowired private TestEntityManager entityManager;

    @Test
    public void testCreateStatesInIndia() {
        Integer countryId = 1;
        Country country = entityManager.find(Country.class, countryId);

//		State state = repo.save(new State("Karnataka", country));
//		State state = repo.save(new State("Punjab", country));
//		State state = repo.save(new State("Uttar Pradesh", country));
        State state = repo.save(new State("West Bengal", country));

        assertThat(state).isNotNull();
        assertThat(state.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateStatesInUS() {
        Integer countryId = 2;
        Country country = entityManager.find(Country.class, countryId);

//		State state = repo.save(new State("California", country));
//		State state = repo.save(new State("Texas", country));
//		State state = repo.save(new State("New York", country));
        State state = repo.save(new State("Washington", country));

        assertThat(state).isNotNull();
        assertThat(state.getId()).isGreaterThan(0);
    }

    @Test
    public void testListStatesByCountry() {
        Integer countryId = 2;
        Country country = entityManager.find(Country.class, countryId);
        List<State> listStates = repo.findByCountryOrderByNameAsc(country);

        listStates.forEach(System.out::println);

        assertThat(listStates.size()).isGreaterThan(0);
    }

    @Test
    public void testUpdateState() {
        Integer stateId = 3;
        String stateName = "Tamil Nadu";
        State state = repo.findById(stateId).get();

        state.setName(stateName);
        State updatedState = repo.save(state);

        assertThat(updatedState.getName()).isEqualTo(stateName);
    }

    @Test
    public void testGetState() {
        Integer stateId = 1;
        Optional<State> findById = repo.findById(stateId);
        assertThat(findById.isPresent());
    }

    @Test
    public void testDeleteState() {
        Integer stateId = 8;
        repo.deleteById(stateId);

        Optional<State> findById = repo.findById(stateId);
        assertThat(findById.isEmpty());
    }
}
