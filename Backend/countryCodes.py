import urllib2
from lxml import html

ht = urllib2.urlopen('http://userpage.chemie.fu-berlin.de/diverse/doc/ISO_3166.html')
tree = html.fromstring(ht.read())
lis= tree.xpath('//li')
f = open('countryCodes.txt', 'w')
for li in lis:
	if li.text != "":
		s = li.text.split('-')
		f.write((s[0].strip()+','+s[1].split(',')[0].strip()+'\n').encode('utf8'))