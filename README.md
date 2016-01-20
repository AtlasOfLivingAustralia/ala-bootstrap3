ala-bootstrap3   [![Build Status](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3.svg?branch=master)](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3)
=========

## Usage
```
runtime ":ala-bootstrap3:1.3"
```

## Description
This is a Grails Plugin to provide the basic set of web resources to correctly apply the **new 2015** ala web theme based on [bootstrap 3.4.4](http://getbootstrap.com)

Many of its features come inherited from the [ala-boostrap2 plugin](https://github.com/AtlasOfLivingAustralia/ala-bootstrap2).

Note: templates, some CSS & JS files are located in the `commonui-bs3` directory. If these have been changed, then the production version will require updating - talk to the sys admin who will update them via Git.

## Grails taglib integration with Bootstrap
This plugin borrows the taglib used in the [Grails Twitter Bootstrap plugin](https://grails.org/plugin/twitter-bootstrap) to modify the way some Core Grails tags are rendered (eg: pagination)

To enable the boostrap compatible rendering add the following line to your `Config.groovy`:

```
grails.plugins.twitterbootstrap.fixtaglib = true
```

## Changelog
* Version **1.1** (07/05/2015)
  * Added latest style changes
  * Upgrades bootstrap dependency to version 3.3.4
  * Added TwitterBootstrapTaglib from the Twitter Bootstrap plugin

* Version **1.0** (04/03/2015)
  * Initial release
