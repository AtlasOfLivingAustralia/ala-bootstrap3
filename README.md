ala-bootstrap3   [![Build Status](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3.svg?branch=master)](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3)
=========
## Grails 3

The Grails 3 version of this plugin can be found on the `grails3` branch of this repo.

## Usage
```
runtime ":ala-bootstrap3:2.0.0"
// Then ensure at least one of resources or the asset pipeline plugin is included
// compile ":resources:1.2.14"
// compile ":asset-pipeline:2.14.1"
```

## Description
This is a Grails Plugin to provide the basic set of web resources to correctly apply the **new 2015** ala web theme based on [bootstrap 3.3.4](http://getbootstrap.com)

**NOTE:** this plugin supports both the resources and asset-pipeline plugins.  If the resources plugin is present as a
dependency then the `main` and `generic` layouts will use the resources plugin by default.  This means that to use the 
asset-pipeline to include the plugin's bundles you must ensure that the resources plugin is not included as a transitive
dependency by any other plugins.  Use `./grailsw dependency-report` to show the full dependency graph if you're unsure
where the resources plugin is coming from.

Many of its features come inherited from the [ala-boostrap2 plugin](https://github.com/AtlasOfLivingAustralia/ala-bootstrap2).

Note: templates, some CSS & JS files are located in the `commonui-bs3` directory. If these have been changed, then the production version will require updating - talk to the sys admin who will update them via Git.

## Grails taglib integration with Bootstrap
This plugin borrows the taglib used in the [Grails Twitter Bootstrap plugin](https://grails.org/plugin/twitter-bootstrap) to modify the way some Core Grails tags are rendered (eg: pagination)

To enable the boostrap compatible rendering add the following line to your `Config.groovy`:

```
grails.plugins.twitterbootstrap.fixtaglib = true
```

## Changelog
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
