<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pom="http://maven.apache.org/POM/4.0.0">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="no"/>
	<!-- 
Generates the edges for a dotty file representing the pom dependencies
Usage:
(
echo "digraph PomDependencies {"
find . -name pom.xml | xargs xsltproc pom2dotty.xslt | sort -u
echo "}"
) > poms.dotty
Afterwards e.g. filtering with tred and plot with GvEdit of GraphViz
 -->
	<xsl:template match="/">
		<xsl:for-each select="/pom:project/pom:dependencies/pom:dependency">
			<xsl:if test="pom:groupId = 'CHANGE THIS TO YOUR GROUP ID'">
				<xsl:choose>
					<xsl:when test="pom:scope = 'compile'">
			&quot;<xsl:value-of select="/pom:project/pom:artifactId"/>&quot; -> &quot;<xsl:value-of select="pom:artifactId"/>&quot; ;<xsl:text>
	</xsl:text>
					</xsl:when>
					<xsl:when test="pom:scope">
				</xsl:when>
					<xsl:otherwise>
			&quot;<xsl:value-of select="/pom:project/pom:artifactId"/>&quot; -> &quot;<xsl:value-of select="pom:artifactId"/>&quot; ;<xsl:text>
	</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
