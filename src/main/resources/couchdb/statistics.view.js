{
	"_id": "_design/statistics",
	"language": "javascript",
	"views": {
		"active_student_users": {
			"map": "function(doc) { if (doc.type === 'skill_question_answer') { emit(doc.user, 1); } }",
			"reduce": "_count"
		},
		"statistics": {
			"map": "function(doc) {\n  switch (doc.type) {\n  case 'session':\n    if (doc.active == 1) { emit('openSessions', 1); }\n    else { emit('closedSessions', 1); }\n    break;\n  case 'skill_question':\n    if (doc.questionType === 'flashcard') {\n      emit('flashcards', 1);\n    } else {\n      if (doc.questionVariant === 'lecture') { emit('lectureQuestions', 1); }\n      else if (doc.questionVariant === 'preparation') { emit('preparationQuestions', 1); }\n      if (doc.piRound == 2) { emit('conceptQuestions', 1); }\n    }\n    break;\n  case 'skill_question_answer':\n    emit('answers', 1);\n    break;\n  case 'interposed_question':\n    emit ('interposedQuestions', 1);\n    break;\n  case 'log':\n    if (doc.event === 'delete') {\n      switch (doc.payload.type) {\n      case 'session':\n        emit('deletedSessions', doc.payload.sessionCount || 1);\n        break;\n      case 'question':\n        emit('deletedQuestions', doc.payload.questionCount || 1);\n        break;\n      case 'answer':\n        emit('deletedAnswers', doc.payload.answerCount || 1);\n        break;\n      case 'comment':\n        emit('deletedComments', doc.payload.commentCount || 1);\n        break;\n      case 'user':\n        emit('deletedUsers', 1);\n        break;\n      }\n    }\n    break;\n  }\n}",
			"reduce": "_sum"
		},
		"unique_session_creators": {
			"map": "function(doc) { if (doc.type === 'session') { emit(doc.creator, 1); } }",
			"reduce": "_count"
		}
	}
}
