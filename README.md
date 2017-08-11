ala-bootstrap3   [![Build Status](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3.svg?branch=master)](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3)
=========

## Usage
```
compile ":ala-bootstrap3:3.0.0-SNAPSHOT"
```

## Description
This is a Grails Plugin to provide the basic set of web assets to correctly apply the **new 2017** ala web theme based on [bootstrap 3.3.4](http://getbootstrap.com)

Many of its features come inherited from the [ala-boostrap2 plugin](https://github.com/AtlasOfLivingAustralia/ala-bootstrap2).

Note: templates, some CSS & JS files are located in the `commonui-bs3` directory. If these have been changed, then the production version will require updating - talk to the sys admin who will update them via Git.

## Grails taglib integration with Bootstrap
This plugin borrows the taglib used in the [Grails Twitter Bootstrap plugin](https://grails.org/plugin/twitter-bootstrap) to modify the way some Core Grails tags are rendered (eg: pagination)

To enable the boostrap compatible rendering add the following line to your `Config.groovy`:

```
grails.plugins.twitterbootstrap.fixtaglib = true
```

## Changelog
* Version **3.0.0** (19/07/2017)
  * Grails 3 support
* Version **2.0.0** (17/07/2017)
  * New 2017 design support
* Version **1.7.0** (10/07/2017)
  * Add initial support for Asset Pipeline
  * Resources plugin is no longer exported as a dependency
* Version **1.1** (07/05/2015)
  * Added latest style changes
  * Upgrades bootstrap dependency to version 3.3.4
  * Added TwitterBootstrapTaglib from the Twitter Bootstrap plugin

* Version **1.0** (04/03/2015)
  * Initial release
