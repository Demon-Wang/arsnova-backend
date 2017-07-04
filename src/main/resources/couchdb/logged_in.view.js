{
	"_id": "_design/logged_in",
	"language": "javascript",
	"views": {
		"visited_sessions_by_user": {
			"map": "function(doc) { if(doc.type == 'logged_in') { emit(doc.user, doc.visitedSessions); }}"
		},
		"all": {
			"map": "function(doc) { if (doc.type == 'logged_in'){ emit(doc.user, doc); }}"
		},
		"by_last_activity_for_guests": {
			"map": "function(doc) {\n  if (doc.type == 'logged_in' && doc.user.indexOf('Guest') === 0) {\n    emit(doc.timestamp || 0, {_rev: doc._rev});\n  }\n}"
		}
	}
}
