# !/usr/bin/env bash
#
# Simple script to convert an AsciiDoc file to MarkDown.
#
# Author:	Matteo Franci <mttfranci@gmail.com>
#
# to run this script :
# 'adoc-to-md.sh ${version}'
# for instance :
# 'adoc-to-md.sh v1.0.0'
#
# NOTE:
#
# requirements :
# - asciidoc cli
# - pandoc cli

VERSION=$1

ADOC=$VERSION.adoc

if [[ -e $ADOC ]]; then
  MD=$VERSION.md
  echo "Converting AsciiDoc to markdown : $ADOC -> $MD"
  asciidoc -b docbook $ADOC
  pandoc -f docbook -t markdown_strict $VERSION.xml -o $MD
else
  echo "Release AsciiDoc file does not exist : $ADOC"
fi
