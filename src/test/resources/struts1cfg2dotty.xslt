<?xml version="1.0" encoding="UTF-8"?>
<!-- Tries to generate a dotty file from struts-1 configuration files -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/struts-config/action-mappings/action/forward">"<xsl:value-of select="../@path" />" -> "<xsl:value-of select="fn:replace(@path,'.do','')"/>";
</xsl:template>
    
    <xsl:template match="/struts-config/action-mappings/action"><xsl:if test="@input" >"<xsl:value-of select="@input" />" -> "<xsl:value-of select="@path"/>";
</xsl:if><xsl:apply-templates/></xsl:template>

</xsl:stylesheet>
