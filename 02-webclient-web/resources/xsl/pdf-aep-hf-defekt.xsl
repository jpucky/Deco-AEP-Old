<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="fo java">
  <xsl:output method="xml" version="1.0" />
  
  <!-- Universelles Hochformat fuer AEP-Statistik-PDFs -->
  <!-- Fuer weitere Erweiterungen siehe auch: http://www.exslt.org/date/index.html -->

  <!-- Globale Variablen -->
  <xsl:variable name="helvetica">
    Helvetica
  </xsl:variable>
  <xsl:variable name="courier">
    Courier
  </xsl:variable>
  <xsl:variable name="tbl-fnt-5pt">
    5pt
  </xsl:variable>
  <xsl:variable name="tbl-fnt-10pt">
    10pt
  </xsl:variable>
  <xsl:variable name="leftstart">
    20mm
  </xsl:variable>
  <xsl:variable name="Zeilenhoehe">
    10pt
  </xsl:variable>
  <xsl:variable name="colorGray1">
    #f0f0f0
  </xsl:variable>
  <xsl:variable name="colorGray2">
    #9fa7b4
  </xsl:variable>
  
  <!-- Einfuegen aller Vorlagen -->
  <xsl:include href="pdf-aep-templates.xsl"/>
  
  <xsl:variable name="datasequence" select="number(90)" />

  <xsl:template match="/">
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

      <!-- Layout-master-set -->
      <fo:layout-master-set>

        <fo:simple-page-master master-name="A4-h" page-height="29.7cm" page-width="21cm"
          margin-top="5mm">
          <fo:region-body region-name="xsl-region-body" />
          <fo:region-before region-name="xsl-region-before" />
          <fo:region-after region-name="xsl-region-after" extent="3cm" />
          <fo:region-start region-name="xsl-region-start" />
          <fo:region-end region-name="xsl-region-end" />
        </fo:simple-page-master>

      </fo:layout-master-set>

      <!-- Metainfos -->
      <fo:declarations>
        <x:xmpmeta xmlns:x="adobe:ns:meta/">
          <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">

            <rdf:Description rdf:about="" xmlns:dc="http://purl.org/dc/elements/1.1/">
              <!-- Dublin Core properties go here -->
              <dc:title>Document title</dc:title>
              <dc:creator>DECODETRON Archiv-Service GmbH</dc:creator>
              <dc:description>
                <xsl:apply-templates select="statistik/listentyp" />
              </dc:description>
            </rdf:Description>

            <rdf:Description rdf:about="" xmlns:xmp="http://ns.adobe.com/xap/1.0/" xmlns:pdf="http://ns.adobe.com/pdf/1.3/">
              <!-- XMP properties go here -->
              <pdf:Keywords>
                AEP, DECODETRON, <xsl:apply-templates select="statistik/listentyp" />
              </pdf:Keywords>
              <xmp:CreatorTool>Internet-Archiv der AEP</xmp:CreatorTool>
            </rdf:Description>

          </rdf:RDF>
        </x:xmpmeta>
      </fo:declarations>

      <!-- <xsl:call-template name="bookmark" /> -->

      <!-- Page Sequence -->
      <xsl:call-template name="data" />

    </fo:root>
  </xsl:template>

  <xsl:template name="data">

    <!-- datasequence -->
    <xsl:for-each select="statistik/data/row[ position() mod $datasequence = 1 ]">

      <!-- PAGE-SEQUENCE-FLOW ANFANG -->
      <fo:page-sequence master-reference="A4-h">

        <fo:static-content flow-name="xsl-region-before">
          <xsl:call-template name="image" />
          <xsl:apply-templates select="../../adressekunde" />
          <xsl:call-template name="headerstatinfo" />

          <fo:block-container absolute-position="absolute" font-size="18pt" font-weight="bold"
            left="{$leftstart}" space-before="100px">
            <fo:block>
              <xsl:apply-templates select="../../listentyp" />
            </fo:block>
          </fo:block-container>

        </fo:static-content>

        <fo:static-content flow-name="xsl-region-after">
          <xsl:call-template name="footerborder" />
          <xsl:call-template name="footer" />
        </fo:static-content>

        <fo:flow flow-name="xsl-region-body">


          <fo:block-container absolute-position="absolute" font-family="Arial" left="{$leftstart}"
            space-before="150px" font-size="5pt" width="17cm">

            <!-- Spaltenueberschrift -->
            <fo:table table-layout="fixed" space-before="3px" font-size="5pt">
            
              <!-- 11-Spalter , Lieferantenname, Artikelbez Breite fix-->
              <fo:table-column column-width="13mm"/>
              <fo:table-column column-width="10mm"/>
              <fo:table-column column-width="10mm"/>
              <fo:table-column column-width="9mm"/><!-- PZN -->
              <fo:table-column column-width="43mm"/><!-- Artikelbez -->
              
              <fo:table-column column-width="7mm"/><!-- Menge -->
              <fo:table-column column-width="7mm"/><!-- Preis -->
              <fo:table-column column-width="7mm"/><!-- Betrag -->
              <fo:table-column column-width="10mm"/>
              <fo:table-column column-width="30mm"/><!-- Lieferantname -->
              
              <fo:table-column />
              
              <fo:table-body>

                <!-- Spaltenueberschrift -->
                <xsl:for-each select="../../dataheader/rowh">
                  <fo:table-row background-color="{$colorGray2}" font-weight="bold" text-align="left" left="5px"
                    height="12px" wrap-option="wrap" linefeed-treatment="preserve">
                    <xsl:for-each select="col">
                      <fo:table-cell padding-start="1px" padding-before="3px" border-left="0px solid white"
                        border-right="0px solid white">
                        <fo:block>
                          <xsl:value-of select="." />
                        </fo:block>
                      </fo:table-cell>
                    </xsl:for-each>
                  </fo:table-row>
                </xsl:for-each>

                <!-- Abstand -->
                <fo:table-row background-color="white" height="2px">
                  <fo:table-cell>
                    <fo:block>
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>

                <xsl:for-each select=". | following::row[position() &lt; $datasequence]">

                  <xsl:variable name="rowColor">
                    <xsl:choose>
                      <xsl:when test="position() mod 2 = 0">
                        white
                      </xsl:when>
                      <xsl:otherwise>
                        #f2f2f2
                      </xsl:otherwise>
                    </xsl:choose>
                  </xsl:variable>

                  <fo:table-row background-color="{$rowColor}" keep-together.within-column="always"
                    wrap-option="no-wrap" space-before="30px"><!-- wrap-option="no-wrap" -->
                    <xsl:for-each select="col">
                      <fo:table-cell overflow="hidden" border-left="0px solid red" border-right="0px solid white"
                        padding-start="1px" padding-after="0pt" display-align="after">
                        <fo:block>
                          <xsl:value-of select="." />
                        </fo:block>
                      </fo:table-cell>
                    </xsl:for-each>
                  </fo:table-row>

                </xsl:for-each>
              </fo:table-body>

            </fo:table>

          </fo:block-container>
        </fo:flow>

      </fo:page-sequence>
      <!-- PAGE-SEQUENCE-FLOW ENDE -->

    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
