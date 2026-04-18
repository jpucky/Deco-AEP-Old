<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="fo java">
  <xsl:output method="xml" version="1.0" />
 


  <xsl:template match="adresseaep">
    <fo:block text-decoration="underline" space-before="0px" font-size="8pt" text-align="right" margin-right="50px">
      <xsl:for-each select="zeile">

        <xsl:choose>
          <xsl:when test="position() = last()">
            <xsl:value-of select="." />
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select="." />
            ,
          </xsl:otherwise>
        </xsl:choose>

      </xsl:for-each>
    </fo:block>
  </xsl:template>

  <xsl:template match="adressekunde">
    <fo:block-container absolute-position="absolute" font-size="{$tbl-fnt-10pt}" left="{$leftstart}"
      top="30pt">
      <xsl:for-each select="zeile">
        <fo:block>
          <xsl:value-of select="." />
        </fo:block>
      </xsl:for-each>
    </fo:block-container>
  </xsl:template>

  <!-- Seitennummer, Datum -->
  <xsl:template name="headerstatinfo">

    <fo:block space-before="0px" font-size="8pt">
      <fo:table width="100%">
        <fo:table-column />
        <fo:table-column column-width="6.5cm" />
        <fo:table-body>
          <fo:table-row>

            <fo:table-cell border="0px solid black">
              <fo:block>
              </fo:block>
            </fo:table-cell>

            <fo:table-cell border="0px solid black">
              <fo:block>

                <fo:block-container>

                  <fo:table width="60mm">

                    <fo:table-column column-width="20mm"></fo:table-column>
                    <fo:table-column column-width="40mm"></fo:table-column>
                    <fo:table-body>


                      <fo:table-row>
                        <fo:table-cell>
                          <fo:block>Ausgabe</fo:block>
                        </fo:table-cell>
                        <fo:table-cell>
                          <fo:block>
                            :
                            <xsl:call-template name="datum" />
                          </fo:block>
                        </fo:table-cell>
                      </fo:table-row>

                      <fo:table-row>
                        <fo:table-cell>
                          <fo:block>Seite</fo:block>
                        </fo:table-cell>
                        <fo:table-cell>
                          <fo:block>
                            :
                            <fo:page-number />
                          </fo:block>
                        </fo:table-cell>
                      </fo:table-row>

                    </fo:table-body>
                  </fo:table>

                </fo:block-container>
              </fo:block>
            </fo:table-cell>

          </fo:table-row>
        </fo:table-body>
      </fo:table>
    </fo:block>

  </xsl:template>

  <xsl:template name="image">
    <fo:block-container text-align="right" margin-right="15px" margin-top="5px">
      <fo:block>
      <!--  
        <fo:external-graphic
          src="url('/image/login-logo-aep.png')"
          content-height="60%" content-width="60%" /> -->
          <fo:external-graphic src="servlet-context:/image/login-logo-aep.png" content-height="60%" content-width="60%"/>
      </fo:block>
    </fo:block-container>
  </xsl:template>

  <xsl:template name="footerborder">
    <fo:block-container absolute-position="absolute" top="0mm" width="100%">
      <fo:block text-align="center">
        <fo:leader top="0mm" leader-pattern="rule" rule-thickness="1px" leader-length="96%" rule-style="solid" />
      </fo:block>
    </fo:block-container>
  </xsl:template>


  <xsl:template name="datum">
    <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('dd.MM.yy'), java:java.util.Date.new())" />
    /
    <xsl:value-of select="java:format(java:java.text.SimpleDateFormat.new('HH:mm:ss'), java:java.util.Date.new())" />
  </xsl:template>

  <xsl:template name="footer">

    <fo:block-container absolute-position="absolute" font-size="5pt" left="50px"
      space-before="15px" font-family="Helvetica">
      <fo:table width="90%">
        <fo:table-column />

        <fo:table-body>
          <fo:table-row>

            <!-- spalte1 -->
            <fo:table-cell border="0px solid black"  line-height="2.5em" text-align="center" wrap-option="no-wrap">
              <fo:block font-weight="bold">AEP GmbH | Industriegebiet Süd A 31 | D-63755 Alzenau | Tel.: +49 6188 9937-370 | Fax: +49 6188 9937-373 | service@aep.de</fo:block>
              <fo:block>Sitz der Gesellschaft: Alzenau | HRB 12522, AG Aschaffenburg | UstID-NR. DE286486044 | Wir haben am 04.10.2016 durch die Regierung von Oberfranken die Großhandelserlaubnis nach §52a Abs. 1 Arzneimittelgesetz in gültiger Fassung erhalten.</fo:block>
              <fo:block>Geschäftsführerin: Dr. Heike Brockmann</fo:block>              
            </fo:table-cell>

          </fo:table-row>
        </fo:table-body>
      </fo:table>
    </fo:block-container>
  </xsl:template>

</xsl:stylesheet>
