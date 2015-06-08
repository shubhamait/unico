package com.unico.services.dao.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.unico.services.dao.GCDDao;

public class GCDDaoImpl implements GCDDao {

	private MongoOperations mongoOps;
	private static final String INPUT_COLLECTION = "INPUT";
	private static final String GCD_COLLECTION = "GCD";
	private static final String INPUT = "input";
	private static final String GCD = "gcd";
	private static final String NULL = "null";
	private static final String ID = "_id";

	public GCDDaoImpl(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
		if (!this.mongoOps.collectionExists(INPUT_COLLECTION)) {
			this.mongoOps.createCollection(INPUT_COLLECTION);
			this.mongoOps.indexOps(INPUT_COLLECTION).ensureIndex(
					new Index().on(INPUT, Direction.ASC));
		}
		if (!this.mongoOps.collectionExists(GCD_COLLECTION)) {
			this.mongoOps.createCollection(GCD_COLLECTION);
			this.mongoOps.indexOps(GCD_COLLECTION).ensureIndex(
					new Index().on(GCD, Direction.ASC));
		}
	}

	@Override
	public void addInputToList(Integer i, Integer j) {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(INPUT, i);
		this.mongoOps.insert(inputMap, INPUT_COLLECTION);
		inputMap = new HashMap<String, Object>();
		inputMap.put(INPUT, j);
		this.mongoOps.insert(inputMap, INPUT_COLLECTION);
	}

	@Override
	public void addGCDToList(Integer gcd) {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(GCD, gcd);
		this.mongoOps.insert(inputMap, GCD_COLLECTION);
	}

	@Override
	public List<Integer> getListofNumbers() {
		final Query query = new Query();
		query.fields().exclude(ID);
		query.with(new Sort(Sort.Direction.ASC, ID));

		return this.mongoOps.execute(INPUT_COLLECTION,
				new CollectionCallback<List<Integer>>() {
					public List<Integer> doInCollection(DBCollection collection) {
						DBCursor cursor = collection.find(query
								.getQueryObject());
						List<Integer> ints = new ArrayList<Integer>();
						while (cursor.hasNext()) {
							ints.add((Integer) cursor.next().get(INPUT));
						}
						return ints;
					}
				});
	}

	@Override
	public List<Integer> listofGCDs() {
		final Query query = new Query();
		query.fields().exclude(ID);
		query.with(new Sort(Sort.Direction.ASC, ID));

		return this.mongoOps.execute(GCD_COLLECTION,
				new CollectionCallback<List<Integer>>() {
					public List<Integer> doInCollection(DBCollection collection) {
						DBCursor cursor = collection.find(query
								.getQueryObject());
						List<Integer> ints = new ArrayList<Integer>();
						while (cursor.hasNext()) {
							ints.add((Integer) cursor.next().get(GCD));
						}
						return ints;
					}
				});
	}

	@Override
	public Integer sumofGCDs() {
		Aggregation agg = newAggregation(group(NULL).sum(GCD).as("total"));
		AggregationResults<Map> result = this.mongoOps.aggregate(agg,
				GCD_COLLECTION, Map.class);
		Map results = result.getMappedResults().get(0);
		return (Integer) results.get("total");
	}
}
