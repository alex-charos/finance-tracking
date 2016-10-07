package gr.charos.homeapp.finance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import gr.charos.homeapp.finance.domain.PersistentFamily;

public interface PersistentFamilyRepository extends MongoRepository<PersistentFamily,String> {

}
 