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
			"questions":[{
				<xsl:for-each select="./*">
					<xsl:apply-templates select = "BinaryQuestion | QuantitativeQuestion | MultipleChoiceQuestion"/>
					<xsl:if test="position() != last()">,</xsl:if>
				</xsl:for-each>  
			}],
			
			"question_script": {
								<xsl:for-each select = "./*">
									<xsl:apply-templates select = "Question"/>
								</xsl:for-each>
			}
		}
	}	
    </xsl:template>
	
	<xsl:template match = "BinaryQuestion">
							"binary_question": {
															"question_id": "<xsl:value-of select = "@questionID"/>",
															"text": "<xsl:value-of select = "Text"/>"
							}
	</xsl:template>

	<xsl:template match = "QuantitativeQuestion">
							"quantitative_question": {
															"question_id": "<xsl:value-of select = "@questionID"/>",
															"text": "<xsl:value-of select = "Text"/>",
															"scale_min_value": "<xsl:value-of select = "scaleMinValue"/>",
															"scale_max_value": "<xsl:value-of select = "scaleMaxValue"/>"
							}
	</xsl:template>
	
	<xsl:template match = "MultipleChoiceQuestion">
							"multiple_choice_question": {
															"question_id": "<xsl:value-of select = "@questionID"/>",
															"text": "<xsl:value-of select = "Text"/>",
															"options": [
																			<xsl:for-each select = "Option">
																			"option":{
																						"num": "<xsl:value-of select = "@num"/>",
																						"text": "<xsl:value-of select = "@text"/>"
																			}<xsl:if test="position() != last()">,</xsl:if>
																			</xsl:for-each>
															]
							}
	</xsl:template>
	
	
	<xsl:template match = "Question">
									"question": {
													"question_id": "<xsl:value-of select = "@questionID"/>",
													<xsl:if test = "OnReply">
														<xsl:apply-templates select = "OnReply"/>
													</xsl:if>
												}
	</xsl:template>
	
	<xsl:template match = "OnReply">
													"on_reply": {
																	"option": "<xsl:value-of select = "@option"/>",
																	<xsl:apply-templates select = "Question"/>
																}
	</xsl:template>
	
</xsl:stylesheet>
