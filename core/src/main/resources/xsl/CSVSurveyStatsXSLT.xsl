<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : CSVSurveyStatsXSLT.xsl
    Created on : 12 June 2018, 10:46
    Author     : <a href="1160936@isep.ipp.pt">Gil Durão</a>
    Description: XSL Document containing the structure of the CSV file containing 
        the statistics related to a survey.
        
Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="text" indent="no"/>

	<xsl:template match="/Survey">
        Survey Statistics
		Survey ID:;<xsl:value-of select = "@surveyID"/>
		;
		; <!-- Three semicolons for Excel Spacing -->
		;
		Questions
        ;
        ; <!-- Three semicolons for Excel Spacing -->
        ;
		BinaryQuestions
		QuestionID;QuestionText;Total;Average;MeanDeviation
		<xsl:for-each select = "Questions/BinaryQuestions/BinaryQuestion">
			<xsl:value-of select = "@questionID"/>;<xsl:value-of select = "QuestionText"/>;<xsl:value-of select = "Total"/>;<xsl:value-of select = "Average"/>;<xsl:value-of select = "MeanDeviation"/>
			<xsl:text>&#xa;</xsl:text>
		</xsl:for-each>
        ;
        ; <!-- Three semicolons for Excel Spacing -->
        ;
		QuantitativeQuestions
		QuestionID;QuestionText;Total;Average;MeanDeviation
		<xsl:for-each select = "Questions/QuantitativesQuestions/QuantitativeQuestion">
			<xsl:value-of select = "@questionID"/>;<xsl:value-of select = "QuestionText"/>;<xsl:value-of select = "Total"/>;<xsl:value-of select = "Average"/>;<xsl:value-of select = "MeanDeviation"/>
			<xsl:text>&#xa;</xsl:text>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
