<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : lg3d-init-1_0.xsl
    Created on : September 29, 2006
    Author     : Sarah Nadi, May Sayed, and Mohamed El-Geish
    Description: XSLT for the initComponent part of the LG3D file
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text" omit-xml-declaration="yes" media-type="text/x-java" indent="no" doctype-public="-//NetBeans//DTD LG3D Frame 1.0//EN" doctype-system="lg3d-1_0.dtd" />
    
    <xsl:template match="/">
        
        <xsl:for-each select="Frame/NonVisualElements/Element">
            <xsl:apply-templates select="." />
        </xsl:for-each>
        
        <xsl:for-each select="Frame/Components/Component">
            <xsl:value-of select="@id" /> = new <xsl:value-of select="@type" />();
            <xsl:apply-templates select="Element" />
        </xsl:for-each>
        
        <xsl:for-each select="Frame/Components/Container">
            <xsl:value-of select="@id" /> = new <xsl:value-of select="@type" />();
        </xsl:for-each>
        // Properties and Event Handlers
        
        <xsl:for-each select="Frame/NonVisualElements/Element">
            <xsl:apply-templates select="Properties" />
        </xsl:for-each>
        
        <xsl:for-each select="Frame/Components/Component">
            <xsl:apply-templates select="Properties" />
            <xsl:apply-templates select="EventHandlers" />
            <xsl:apply-templates select="Element/Properties" />
            <xsl:value-of select="@id" />.addChild(<xsl:value-of select="Element/@id" />);
        </xsl:for-each>
        
        <xsl:for-each select="Frame/Components/Container">
            <xsl:apply-templates select="Properties" />
            <xsl:apply-templates select="EventHandlers" />
        </xsl:for-each>
        
        <xsl:apply-templates select="Frame/Properties" />
        // Layout
        
        <xsl:for-each select="Frame/Layout/ComponentRef">
            <xsl:text>this.addChild(</xsl:text>
            <xsl:value-of select="@ref" />);
        </xsl:for-each>
        <xsl:for-each select="Frame/Layout/ContainerRef">
            <xsl:apply-templates select="." />
            <xsl:text>this.addChild(</xsl:text>
            <xsl:value-of select="@ref" />);
        </xsl:for-each>
        
    </xsl:template>
    
    <!-- Element Template -->
    
    <xsl:template match="Element">
        <xsl:value-of select="@id" />
        <xsl:text> = </xsl:text>
        <xsl:apply-templates select="Parameters" />;
    </xsl:template>
    
    <!-- Parameters Template -->
    
    <xsl:template match="Parameters">
        <xsl:text>new </xsl:text>
        <xsl:value-of select="../@type" />
        <xsl:text>(</xsl:text>
        <xsl:for-each select="*">
            <xsl:choose>
                <xsl:when test="name() = 'SimpleParameter'">
                    <xsl:choose>
                        <xsl:when test="@type = 'java.lang.String'">"<xsl:value-of select="@value" />"</xsl:when>
                        <xsl:when test="@type = 'java.lang.Class'"><xsl:value-of select="@value" />.class</xsl:when>
                        <xsl:otherwise><xsl:value-of select="@value" /></xsl:otherwise>
                    </xsl:choose>
                </xsl:when>
                <xsl:when test="name() = 'ComplexParameter'">
                    <xsl:apply-templates select="Parameters" />
                </xsl:when>
            </xsl:choose>
            <xsl:if test="position() != last()">, </xsl:if>
        </xsl:for-each>
        <xsl:text>)</xsl:text>
    </xsl:template>
    
    <!-- Properties Template -->
    
    <xsl:template match="Properties">
        <xsl:for-each select="SimpleProperty">
            <xsl:value-of select="../../@id" />
            <xsl:text>.set</xsl:text>
            <xsl:value-of select="@name" />(<xsl:value-of select="@value" />);
        </xsl:for-each>
        <xsl:for-each select="ComplexProperty">
            <xsl:value-of select="../../@id" />
            <xsl:text>.set</xsl:text>
            <xsl:value-of select="@name" />
            <xsl:text>(</xsl:text>
            <xsl:apply-templates select="Parameters" />);
        </xsl:for-each>
    </xsl:template>
    
    <!-- EventHandlers Template -->
        
    <xsl:template match="EventHandlers">
        <xsl:for-each select="EventHandler">
            <xsl:value-of select="../../@id" />.addListener(new org.jdesktop.lg3d.wg.event.LgEventListener() {
            public Class[] getTargetEventClasses() {
            return new Class[] {<xsl:value-of select="@event" />.class};
            }
            
            public void processEvent(org.jdesktop.lg3d.wg.event.LgEvent lgEvent) {
            <xsl:value-of select="@handler" />((<xsl:value-of select="@event" />) lgEvent);
            }
            });
        </xsl:for-each>
    </xsl:template>
    
    <!-- ContainerRef Template -->
    
    <xsl:template match="ContainerRef">
        <xsl:for-each select="ComponentRef">
            <xsl:value-of select="../@ref" />.addChild(<xsl:value-of select="@ref" />);
        </xsl:for-each>
        <xsl:for-each select="ContainerRef">
            <xsl:apply-templates select="." />
            <xsl:value-of select="../@ref" />.addChild(<xsl:value-of select="@ref" />);
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>