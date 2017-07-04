{
	"_id": "_design/user",
	"language": "javascript",
	"views": {
		"doc_by_username": {
			"map": "function(doc) { if (doc.type == 'userdetails') emit(doc.username, doc); }"
		},
		"by_creation_for_inactive": {
			"map": "function(doc) {\n  if (doc.type == 'userdetails' && doc.activationKey) {\n    emit(doc.creation, {_rev: doc._rev});\n  }\n}"
		}
	}
}
