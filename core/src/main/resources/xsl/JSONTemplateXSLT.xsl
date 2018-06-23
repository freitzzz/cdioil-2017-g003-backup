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
    {"template":{
			"title": "<xsl:value-of select="@name"/>",
		<xsl:apply-templates/>
		}
	}	
	</xsl:template>

	<xsl:template match = "Questions">
			"questions":[
		<xsl:for-each select="BinaryQuestion | QuantitativeQuestion | MultipleChoiceQuestion">
			<xsl:apply-templates select = "."/>
			<xsl:if test="position() != last()">,</xsl:if>
		</xsl:for-each>  
					]<xsl:if test="following-sibling::*">,</xsl:if>
	</xsl:template>

	<xsl:template match = "BinaryQuestion">
							{"binary_question": {
															"question_id": "<xsl:value-of select = "@questionID"/>",
															"text": "<xsl:value-of select = "Text"/>"
							}}
	</xsl:template>

	<xsl:template match = "QuantitativeQuestion">
							{"quantitative_question": {
															"question_id": "<xsl:value-of select = "@questionID"/>",
															"text": "<xsl:value-of select = "Text"/>",
															"scale_min_value": "<xsl:value-of select = "scaleMinValue"/>",
															"scale_max_value": "<xsl:value-of select = "scaleMaxValue"/>"
							}}
	</xsl:template>

	<xsl:template match = "MultipleChoiceQuestion">
							{"multiple_choice_question": {
															"question_id": "<xsl:value-of select = "@questionID"/>",
															"text": "<xsl:value-of select = "Text"/>",
															"options": [
		<xsl:for-each select = "Option">
																			{"option":{
																						"num": "<xsl:value-of select = "@num"/>",
																						"text": "<xsl:value-of select = "@text"/>"
																			}}<xsl:if test="position() != last()">,</xsl:if>
		</xsl:for-each>
															]
							}}
	</xsl:template>

	<xsl:template match = "SurveyItems">
			"survey_items": [
		<xsl:for-each select = "Category | Product">
			<xsl:apply-templates select = "."/>
			<xsl:if test="position() != last()">,</xsl:if>
		</xsl:for-each>
		]<xsl:if test="following-sibling::*">,</xsl:if>
	</xsl:template>

	<xsl:template match = "Category">
								{"category": {
												"path": "<xsl:value-of select = "@path"/>"
								}}
	</xsl:template>

	<xsl:template match = "Product">
								{"product": {
												"sku": "<xsl:value-of select = "@sku"/>"
								}}
	</xsl:template>

	<xsl:template match = "QuestionScript">
			"question_script": {
		<xsl:for-each select = "Question">
			<xsl:apply-templates select = "."/>
			<xsl:if test="position() != last()">,</xsl:if>
		</xsl:for-each>
			}
	</xsl:template>	

	<!--A Question element may or may not have a list of onReply elements-->
	<xsl:template match = "Question">
									"question": {
													"question_id": "<xsl:value-of select = "@questionID"/>"
		<xsl:if test = "OnReply">
							,"on_reply_array":[<xsl:for-each select = "OnReply">
								<xsl:apply-templates select = "."/>
								<xsl:if test="position() != last()">,</xsl:if>
							</xsl:for-each>
							]<xsl:if test="position() != last()">,</xsl:if>
		</xsl:if>
												}
	</xsl:template>

	<!--An onReply element always has a Question element-->
	<xsl:template match = "OnReply">
													{"on_reply": {
																	"option": "<xsl:value-of select = "@option"/>",
		<xsl:apply-templates select = "Question"/>
																}}
	</xsl:template>

</xsl:stylesheet>
