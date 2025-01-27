<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : JSONSurveyReviewsXSLT.xsl
    Created on : 11 de Junho de 2018, 15:25
    Author     : <a href="1160907@isep.ipp.pt">João Freitas</a>
    Description: XSL Document containing the structure of the JSON file containing 
        all Reviews of Survey that will be transformed with XSLT
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="3.0">
    <xsl:output method="text" indent="yes"/>
    
    <!-- TODO customize transformation rules
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/Survey">
        <xsl:variable name="suggestionsNumber" select="count(Suggestions/*)"/>
        {
            "id":"<xsl:value-of select="@id"/>",
            "reviews":"<xsl:value-of select="@reviews"/>",
            <xsl:for-each select="Question"> <!-- Iterates through all Survey Question Labels -->
                "question":{
                    "id":"<xsl:value-of select="@id"/>",
                    "type":"<xsl:value-of select="@type"/>",
                    "name":"<xsl:value-of select="@name"/>",<!-- Selects the value of the attribute "name" -->
                    "answers":[
                    <xsl:for-each select="Answer"> <!-- Iterates all "Answer" nodes -->
                        {
                            "answer":"<xsl:value-of select="."/>" <!-- Selects the node internal value -->
                        }<xsl:if test="position() != last()">,</xsl:if> <!-- Checks if the current node position is different from the last of the 
                                                                                 the nodes being iterated. If so adds a colon, else doesn't add since it's the last element -->
                    </xsl:for-each>
                    ]
                }<xsl:choose> <!-- choose -> if|else -->
                        <xsl:when test="position()=last()"> <!-- if the current node position is the same as the last position -->
                            <xsl:if test="$suggestionsNumber">,</xsl:if> <!-- If there are any suggestions add a colen -->
                        </xsl:when>
                        <xsl:otherwise>,</xsl:otherwise> <!-- If hte node position is different from the last add a colon -->
                    </xsl:choose>
        </xsl:for-each>
            <xsl:if test="$suggestionsNumber">
                <xsl:apply-templates select="Suggestions"/>
            </xsl:if>
        }
    </xsl:template>
    
    <!-- Matches Survey Reviews Suggestions -->
    <xsl:template match="Suggestions">
            "suggestions":[
            <xsl:for-each select="Suggestion">
                {
                    "suggestion":"<xsl:value-of select="@value"/>"
                }<xsl:if test="position() != last()">,</xsl:if>
            </xsl:for-each>
            ]
    </xsl:template>
</xsl:stylesheet>
