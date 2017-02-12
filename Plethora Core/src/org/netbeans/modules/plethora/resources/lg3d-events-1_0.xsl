<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : lg3d-events-1_0.xsl
    Created on : September 29, 2006
    Author     : Sarah Nadi, May Sayed, and Mohamed El-Geish
    Description: XSLT for the events part of the LG3D file
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text" omit-xml-declaration="yes" media-type="text/x-java" indent="no" doctype-public="-//NetBeans//DTD LG3D Frame 1.0//EN" doctype-system="lg3d-1_0.dtd" />

    <xsl:template match="/">
               
        <xsl:for-each select="Frame/Components/Component">
            <xsl:apply-templates select="EventHandlers" />
        </xsl:for-each>
        
        <xsl:for-each select="Frame/Components/Container">
            <xsl:apply-templates select="EventHandlers" />
        </xsl:for-each>
        
    </xsl:template>

    <!-- EventHandlers Template -->
    
    <xsl:template match="EventHandlers">
        <xsl:for-each select="EventHandler">
            <xsl:text>private void </xsl:text>
            <xsl:value-of select="@handler" />(<xsl:value-of select="@event" /> lgEvent) {
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>