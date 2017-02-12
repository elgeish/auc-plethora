<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : lg3d-vars-1_0.xsl
    Created on : September 29, 2006
    Author     : Sarah Nadi, May Sayed, and Mohamed El-Geish
    Description: XSLT for the variables section of the LG3D file
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text" omit-xml-declaration="yes" media-type="text/x-java" indent="no" doctype-public="-//NetBeans//DTD LG3D Frame 1.0//EN" doctype-system="lg3d-1_0.dtd" />
    
    <xsl:template match="/">
        <xsl:for-each select="Frame/Components/Container">
            <xsl:text>private </xsl:text><xsl:value-of select="@type" /><xsl:text> </xsl:text><xsl:value-of select="@id" />;
        </xsl:for-each>
        <xsl:for-each select="Frame/Components/Component">
            <xsl:text>private </xsl:text><xsl:value-of select="@type" /><xsl:text> </xsl:text><xsl:value-of select="@id" />;
            <xsl:text>private </xsl:text><xsl:value-of select="Element/@type" /><xsl:text> </xsl:text><xsl:value-of select="Element/@id" />;
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>