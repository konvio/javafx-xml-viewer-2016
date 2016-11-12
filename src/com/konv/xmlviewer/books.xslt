<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
  <h2>Library</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th style="text-align:left">ISBN</th>
        <th style="text-align:left">Title</th>
        <th style="text-align:left">Author</th>
        <th style="text-align:left">Annotation</th>
        <th style="text-align:left">Reader</th>
        <th style="text-align:left">Price</th>
      </tr>
      <xsl:for-each select="library/book">
      <tr>
        <td><xsl:value-of select="isbn"/></td>
        <td><xsl:value-of select="title"/></td>
        <td><xsl:value-of select="author"/></td>
        <td><xsl:value-of select="annotation"/></td>
        <td><xsl:value-of select="reader"/></td>
        <td><xsl:value-of select="price"/></td>
      </tr>
      </xsl:for-each>
    </table>
</xsl:template>
</xsl:stylesheet>