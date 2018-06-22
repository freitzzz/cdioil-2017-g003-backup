<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="3.0">

	<!--
    Document   : CSVTemplateXSLT.xsl
    Created on : 14 de Junho de 2018, 21:26
    Authors    : António Sousa (1161371), Rita Gonçalves (1160912)
    Description: XSL Transformer that transforms a XML document into a CSV file.
        Purpose of transformation follows.
	-->


	<xsl:output method="text" indent="no"/>

	<xsl:template match="/Template">
		Template Information
		Template title:;<xsl:value-of select= "@name"/>
		;<!-- Semicolon for Excel spacing -->
		Questions:
		<xsl:for-each select = "./*">
			<xsl:apply-templates select = "BinaryQuestion | QuantitativeQuestion | MultipleChoiceQuestion">
			;<!-- Semicolon for Excel spacing -->
			</xsl:apply-templates>
		</xsl:for-each>
		QuestionScript
		<xsl:for-each select = "./*">
			<xsl:apply-templates select = "Question"/>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match = "BinaryQuestion">
	;BinaryQuestion   
	;QuestionID;<xsl:value-of select = "@questionID"/>;Text;<xsl:value-of select = "Text"/>
	</xsl:template>

	<xsl:template match = "QuantitativeQuestion">
	;QuantitativeQuestion;
	;QuestionID;<xsl:value-of select = "@questionID"/>;Text;<xsl:value-of select = "Text"/>
	;scaleMinValue;<xsl:value-of select = "scaleMinValue"/>;scaleMaxValue;<xsl:value-of select = "scaleMaxValue"/>
	</xsl:template>

	<xsl:template match = "MultipleChoiceQuestion">
	;MultipleChoiceQuestion
	;QuestionID;<xsl:value-of select = "@questionID"/>;Text;<xsl:value-of select = "Text"/>
	;Options:<xsl:for-each select="Option">
	;num;<xsl:value-of select = "@num"/>;text;<xsl:value-of select = "@text"/>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match = "Question">
	;QuestionID;<xsl:value-of select = "@questionID"/>
		<xsl:if test = "OnReply">
			<xsl:apply-templates select = "OnReply"/>
		</xsl:if>
	</xsl:template>

	<xsl:template match = "OnReply">
	;OnReply;option<xsl:value-of select = "@option"/>
		<xsl:apply-templates select = "Question"/>
	</xsl:template>

</xsl:stylesheet>
