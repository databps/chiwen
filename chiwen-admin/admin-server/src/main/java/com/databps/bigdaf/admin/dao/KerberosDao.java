package com.databps.bigdaf.admin.dao;

import com.databps.bigdaf.admin.domain.Kerberos;
import com.databps.bigdaf.admin.domain.UGroup;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class KerberosDao {

	@Autowired
	private MongoOperations mongoOperations;
	private static String collectionName = "kerberos_config";

	public Kerberos findKerberosConfig(String cmpyId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
		Kerberos kerberos = mongoOperations.findOne(query, Kerberos.class, collectionName);
		return kerberos;
	}

	public void saveKerberos(Kerberos conf) {
		mongoOperations.save(conf, collectionName);
	}

	public void deleteKerberos(String cmpyId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cmpy_id").is(cmpyId));
		mongoOperations.remove(query,collectionName);
	}

}
