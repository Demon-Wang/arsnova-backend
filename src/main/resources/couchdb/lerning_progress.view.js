{
	"_id": "_design/learning_progress",
	"language": "javascript",
		"views": {
			"question_value_achieved_for_user": {
				"comment": "This view returns the points users scored for answered questions.",
				"map": "function(doc) { if (doc.type === 'skill_question_answer' && doc.abstention !== true) {/*The 'questionValue' contains the points scored with this answer, and this could be negative if a wrong answer was given. However, we do not want negative values, so we set the lower bound to 0.*/score = Math.max(doc.questionValue || 0, 0); emit([doc.sessionId, doc.user], { questionId: doc.questionId, score: score, piRound: doc.piRound }); } }"
			},
			"maximum_value_of_question": {
				"comment": "This view returns the maximum number that can be achieved when answering this question.",
				"map": "function(doc) {\n  /* The question's value is determined by the maximum of all possibleAnswer values. We assume that a correct answer is assigned a positive value, while a negative value suggests a wrong answer. The goal then is to get the highest possible value. This leaves us with two cases: 1) On any single choice question, the value is the maximum of all possibleAnswer values. 2) On a multiple choice question, we add up all positive values. */\n  var value = 0, answers = [], positiveAnswers = [], score = 0;\n  if (doc.type === 'skill_question' && ['school', 'flashcard'].indexOf(doc.questionType) === -1) {\n    if ('freetext' === doc.questionType && !doc.fixedAnswer) { return; }\n    answers = doc.possibleAnswers.map(function(answer) { return answer.value || 0; });\n    /* find the maximum value */\n   if (doc.fixedAnswer) value = doc.rating; else value = Math.max.apply(null, [0].concat(answers));\n    /* ignore likert ('vote') questions without any points */\n    if (doc.questionType === 'vote' && value === 0) { return; }\n    /* special case for mc and grid questions: add up all positive answers. */\n    if (['grid', 'mc'].indexOf(doc.questionType) !== -1) {\n      positiveAnswers = answers.filter(function(val) { return val >= 0; });\n      if (positiveAnswers.length > 0) {\n        value = positiveAnswers.reduce(function(prev, cur) { return prev + cur; }, 0);\n      }\n    }\n    emit([doc.sessionId, doc._id], { value: value, questionVariant: doc.questionVariant, piRound: doc.piRound });\n  }\n}"
			}
		}
}
