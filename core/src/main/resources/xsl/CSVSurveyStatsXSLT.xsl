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

	<xsl:variable name = "SEPARATOR" select = "';'"/>
	<xsl:variable name = "NEW_LINE" select = "'&#10;'"/>
	<xsl:template match="/Survey">
        <xsl:text>Survey Statistics</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:value-of select = "$NEW_LINE"/>
		<xsl:text>Survey ID</xsl:text><xsl:value-of select = "$SEPARATOR"/><xsl:value-of select = "@surveyID"/>
		<xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/>
        <xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/> <!-- Three semicolons for Excel Spacing -->
        <xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/>
        <xsl:value-of select="$NEW_LINE"/>
		<xsl:text>Question List</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/>
        <xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/> <!-- Three semicolons for Excel Spacing -->
        <xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/>
        <xsl:value-of select="$NEW_LINE"/>
		<xsl:text>Binary Questions</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:value-of select = "$NEW_LINE"/>
		<xsl:text>Question ID</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Question Text</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Total Answers</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Average</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Standard Deviation</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:value-of select = "$NEW_LINE"/>
		<xsl:for-each select = "Questions/BinaryQuestions/BinaryQuestion">
			<xsl:value-of select = "$NEW_LINE"/>
			<xsl:value-of select = "@questionID"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "QuestionText"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "Total"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "Average"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "MeanDeviation"/><xsl:value-of select = "$SEPARATOR"/>
		</xsl:for-each>
        <xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/>
        <xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/> <!-- Three semicolons for Excel Spacing -->
        <xsl:value-of select="$NEW_LINE"/>
        <xsl:value-of select="$SEPARATOR"/>
        <xsl:value-of select="$NEW_LINE"/>
		<xsl:text>Quantitative Questions</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:value-of select = "$NEW_LINE"/>
		<xsl:text>Question ID</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Question Text</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Total Answers</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Average</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:text>Standard Deviation</xsl:text><xsl:value-of select = "$SEPARATOR"/>
		<xsl:value-of select = "$NEW_LINE"/>
		<xsl:for-each select = "Questions/QuantitativesQuestions/QuantitativeQuestion">
			<xsl:value-of select = "$NEW_LINE"/>
			<xsl:value-of select = "@questionID"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "QuestionText"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "Total"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "Average"/><xsl:value-of select = "$SEPARATOR"/>
			<xsl:value-of select = "MeanDeviation"/><xsl:value-of select = "$SEPARATOR"/>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
