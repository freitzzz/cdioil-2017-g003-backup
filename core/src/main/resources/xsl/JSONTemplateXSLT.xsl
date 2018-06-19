<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : JSONTemplateXSLT.xsl
    Created on : 14 de Junho de 2018, 21:20
    Authors    : António Sousa (1161371), Ana Guerra (1161191)
    Description: XSL Transformer that transforms a XML document into a JSON file.
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="3.0">
	<xsl:output method="text" indent="yes"/>

	<xsl:template match="/Template">
	{
		"title":"<xsl:value-of select="@title"/>",
            	"questions":
			"questions":[
		<xsl:for-each select="Question">
			"question":{
				"id":"<xsl:value-of select="@ID"/>",
				"questiontext":"<xsl:value-of select="QuestionText"/>",
				"options":
					"options":[
					<xsl:for-each select="Options/Option">
						"option":{
					<xsl:value-of select = "."/>,
						},
					</xsl:for-each>
						]
				},
			</xsl:for-each>
				]					
		}   
    	</xsl:template>

</xsl:stylesheet>
