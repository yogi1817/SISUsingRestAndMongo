package com.sis.rest.dao.repository.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.sis.rest.dao.repository.UserOperations;
import com.sis.rest.pojo.User;

/**
 * @author 618730
 *
 */
public class UserRepositoryImpl implements UserOperations {

	@Autowired
	private MongoOperations mongo;

	private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);
	
	@Override
	public User getAttendanceForUserIdAndSubject(String subjectName, String userName) {
		Aggregation agg = newAggregation(
				match(where("userId").is(userName)),
				unwind("attendance"),
				match(where("attendance.subjectName")
						.is(subjectName)),
				group().addToSet("attendance").as("attendance"));
		logger.info("agg in getAttendance "+agg);

		AggregationResults<User> userResult = 
				mongo.aggregate(agg, User.class, User.class);

		User user = userResult.getUniqueMappedResult(); 
		return user;
	}
}
