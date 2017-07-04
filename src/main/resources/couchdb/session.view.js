{
	"_id": "_design/session",
	"language": "javascript",
	"views": {
		"by_courseid": {
			"map": "function(doc) { if(doc.type == 'session' && doc.courseId  && doc.sessionType != 'public_pool') emit(doc.courseId, null); }"
		},
		"by_keyword": {
			"map": "function(doc) { if(doc.type == 'session') emit(doc.keyword, null); }"
		},
		"partial_by_sessiontype_creator_name": {
			"map": "function(doc) { if(doc.type == 'session') emit([doc.sessionType, doc.creator, doc.name], {shortName: doc.shortName, keyword: doc.keyword, active: doc.active, courseType: doc.courseType, creationTime: doc.creationTime}); }"
		},
		"partial_by_ppsubject_name_for_publicpool": {
			"map": "function(doc) { if(doc.type == 'session' && doc.sessionType == 'public_pool') emit([doc.ppSubject, doc.name], {ppSubject: doc.ppSubject,name: doc.name, keyword: doc.keyword, ppLevel: doc.ppLevel}); }"
		},
		"by_lastactivity_for_guests": {
			"map": "function(doc) {\n  if (doc.type === 'session' && doc.sessionType !== 'public_pool' && doc.creator.indexOf('Guest') === 0) {\n    emit(doc.lastOwnerActivity || doc.creationTime, {_rev: doc._rev});\n  }\n}"
		}
	}
}
