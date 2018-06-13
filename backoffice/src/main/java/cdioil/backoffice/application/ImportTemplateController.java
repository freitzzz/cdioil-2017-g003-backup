///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package cdioil.backoffice.application;
//
//import cdioil.application.utils.SurveyTemplate;
//import cdioil.application.utils.SurveyTemplate.Question;
//import cdioil.application.utils.SurveyTemplate.BinaryQuestion;
//import cdioil.application.utils.SurveyTemplate.MultipleChoiceQuestion;
//import cdioil.application.utils.SurveyTemplate.OnReply;
//import cdioil.application.utils.SurveyTemplate.OnReplyQuestion;
//import cdioil.application.utils.SurveyTemplate.Option;
//import cdioil.application.utils.SurveyTemplate.QuantitiveQuestion;
//import cdioil.application.utils.SurveyTemplate.SurveyItem;
//import cdioil.domain.Category;
//import cdioil.domain.QuantitativeQuestion;
//import cdioil.domain.QuantitativeQuestionOption;
//
//import cdioil.domain.QuestionGroup;
//import cdioil.domain.QuestionOption;
//import cdioil.domain.ScriptedTemplate;
//import cdioil.domain.Template;
//import cdioil.files.CommonFileExtensions;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//
///**
// * Controller for US-236 (import template script from XML file).
// *
// * @author António Sousa [1161371]
// */
public class ImportTemplateController {
//
//    /**
//     * Imports a template script from a XML file.
//     *
//     * @param filePath Path of the file to import
//     * @return true, if the template is successfully imported. Otherwise, returns false.
//     */
//    public boolean importTemplate(String filePath) {
//        if (filePath == null || filePath.isEmpty() || !filePath.endsWith(CommonFileExtensions.XML_EXTENSION)) {
//            return false;
//        }
//
//        try {
//            JAXBContext jc = JAXBContext.newInstance(SurveyTemplate.class);
//
//            Unmarshaller unmarshaller = jc.createUnmarshaller();
//            SurveyTemplate surveyTemplate = (SurveyTemplate) unmarshaller.unmarshal(new File(filePath));
//
//            String name = surveyTemplate.getName();
//            List<Question> script = surveyTemplate.getTemplateQuestionScript().getOnReplyQuestions();
//            List<Question> questions = surveyTemplate.getTemplateQuestions().getQuestions();
//            List<SurveyItem> surveyItems = surveyTemplate.getTemplateSurveyItems().getSurveyItems();
//
//            String path = "";
//            for (SurveyItem s : surveyItems) {
//                if (s instanceof cdioil.application.utils.SurveyTemplate.Category) {
//                    path = ((cdioil.application.utils.SurveyTemplate.Category) s).getPath();
//                }
//            }
//
//            if (path.isEmpty()) {
//                return false;
//            }
//
//            String questionGroupName = path + " Group";
//            QuestionGroup questionGroup = new QuestionGroup(questionGroupName);
//
//            for (Question q : questions) {
//                String questionText = q.getText();
//                String questionID = q.getID();
//                if (q instanceof cdioil.application.utils.SurveyTemplate.QuantitiveQuestion) {
//                    cdioil.application.utils.SurveyTemplate.QuantitiveQuestion quantitaiveQuestion = (cdioil.application.utils.SurveyTemplate.QuantitiveQuestion) q;
//
//                    List<QuestionOption> optionList = new ArrayList<>();
//                    for (double scaleValue = quantitaiveQuestion.getMaximumValueScale(); scaleValue <= quantitaiveQuestion.getMaximumValueScale(); scaleValue++) {
//                        cdioil.domain.QuestionOption option = new cdioil.domain.QuantitativeQuestionOption(scaleValue);
//                        optionList.add(option);
//                    }
//                    cdioil.domain.QuantitativeQuestion question = new cdioil.domain.QuantitativeQuestion(questionText, questionID, optionList);
//                    questionGroup.addQuestion(question);
//
//                } else if (q instanceof cdioil.application.utils.SurveyTemplate.BinaryQuestion) {
//                    cdioil.domain.BinaryQuestion question = new cdioil.domain.BinaryQuestion(questionText, questionID);
//                    questionGroup.addQuestion(question);
//
//                } else if (q instanceof cdioil.application.utils.SurveyTemplate.MultipleChoiceQuestion) {
//                    cdioil.application.utils.SurveyTemplate.MultipleChoiceQuestion multipleChoiceQuestion = (cdioil.application.utils.SurveyTemplate.MultipleChoiceQuestion) q;
//                    List<QuestionOption> optionList = new ArrayList<>();
//                    for (Option op : multipleChoiceQuestion.getOptionsList()) {
//                        QuestionOption option = new cdioil.domain.MultipleChoiceQuestionOption(op.getOptionText());
//                    }
//                    cdioil.domain.MultipleChoiceQuestion question = new cdioil.domain.MultipleChoiceQuestion(questionText, questionID, optionList);
//                    questionGroup.addQuestion(question);
//                }
//            }
//
//            ScriptedTemplate template = new ScriptedTemplate(name, questionGroup);
//
//            for (Question q : script) {
//                if (q instanceof cdioil.application.utils.SurveyTemplate.OnReplyQuestion) {
//
//                    OnReplyQuestion onReplyQuestion = (OnReplyQuestion) q;
//
//                    OnReply reply = onReplyQuestion.getReplyData();
//                    OnReplyQuestion nextQuestion = reply.getNextQuestion();
//                    String options = reply.getOnReplyOptions();
//
//                    for (cdioil.domain.Question question : questionGroup.getQuestions()) {
//                        cdioil.domain.Question origin, next;
//                        if (q.getID().equals(question.getQuestionID())) {
//                            origin = question;
//                        }
//
//                        if (nextQuestion.getID().equals(q.getID())) {
//                            next = question;
//                        }
//                        
//                        
//                        
//                    }
//
//                    cdioil.application.utils.SurveyTemplate.QuantitiveQuestion quantitaiveQuestion = (cdioil.application.utils.SurveyTemplate.QuantitiveQuestion) q;
//
//                    List<QuestionOption> optionList = new ArrayList<>();
//                    for (double scaleValue = quantitaiveQuestion.getMaximumValueScale(); scaleValue <= quantitaiveQuestion.getMaximumValueScale(); scaleValue++) {
//                        cdioil.domain.QuestionOption option = new cdioil.domain.QuantitativeQuestionOption(scaleValue);
//                        optionList.add(option);
//                    }
//                    cdioil.domain.QuantitativeQuestion question = new cdioil.domain.QuantitativeQuestion(questionText, questionID, optionList);
//
//                    if (q instanceof cdioil.application.utils.SurveyTemplate.QuantitiveQuestion) {
//                        cdioil.application.utils.SurveyTemplate.QuantitiveQuestion quantitaiveQuestion2 = (cdioil.application.utils.SurveyTemplate.QuantitiveQuestion) q;
//
//                        List<QuestionOption> optionList2 = new ArrayList<>();
//                        for (double scaleValue = quantitaiveQuestion2.getMaximumValueScale(); scaleValue <= quantitaiveQuestion2.getMaximumValueScale(); scaleValue++) {
//                            cdioil.domain.QuestionOption option = new cdioil.domain.QuantitativeQuestionOption(scaleValue);
//                            optionList2.add(option);
//                        }
//                        cdioil.domain.QuantitativeQuestion question23232 = new cdioil.domain.QuantitativeQuestion(questionText, questionID, optionList2);
//                        questionGroup.addQuestion(question23232);
//
//                    } else if (q instanceof cdioil.application.utils.SurveyTemplate.BinaryQuestion) {
//                        cdioil.domain.BinaryQuestion heiufudsfniofio = new cdioil.domain.BinaryQuestion(questionText, questionID);
//                        questionGroup.addQuestion(heiufudsfniofio);
//
//                    } else if (q instanceof cdioil.application.utils.SurveyTemplate.MultipleChoiceQuestion) {
//                        cdioil.application.utils.SurveyTemplate.MultipleChoiceQuestion multipleChoiceQuestion = (cdioil.application.utils.SurveyTemplate.MultipleChoiceQuestion) q;
//                        List<QuestionOption> sdfkjdnfjdsfd = new ArrayList<>();
//                        for (Option op : multipleChoiceQuestion.getOptionsList()) {
//                            QuestionOption option = new cdioil.domain.MultipleChoiceQuestionOption(op.getOptionText());
//                        }
//                        cdioil.domain.MultipleChoiceQuestion bfudsfbkdsfhkdsf = new cdioil.domain.MultipleChoiceQuestion(questionText, questionID, sdfkjdnfjdsfd);
//                        questionGroup.addQuestion(bfudsfbkdsfhkdsf);
//                    }
//                }
//            }
//
//        } catch (JAXBException ex) {
//            Logger.getLogger(ImportTemplateController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
}
