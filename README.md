ala-bootstrap3   [![Build Status](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3.svg?branch=master)](https://travis-ci.org/AtlasOfLivingAustralia/ala-bootstrap3)
=========
## Grails 4
The version of 4.0.0-SNAPSHOT with Grails 4.0.13 can be found on the `grails4` branch of this repo.

## Grails 3

The Grails 3 version of this plugin can be found on the `master` branch of this repo.

## Grails 2

The Grails 2 version of this plugin can be found on the `grails2` branch of this repo.

## Usage
```
compile ":ala-bootstrap3:4.0.0-SNAPSHOT"
```

## Description
This is a Grails Plugin to provide the basic set of web assets to correctly apply the **new 2017** ala web theme based on [bootstrap 3.3.4](http://getbootstrap.com)

Many of its features come inherited from the [ala-boostrap2 plugin](https://github.com/AtlasOfLivingAustralia/ala-bootstrap2).

Note: templates, some CSS & JS files are located in the `commonui-bs3` git repository.

## Integration with ALA-Auth plugin

This plugin assumes but does not directly depend on the ala-auth plugin.  It switches login URL behaviour based on the `security.oidc.enabled` config property, using the ala-auth provided login controller instead of a redirect straight to the configured CAS server.

### Use without the ALA-Auth plugin

To use custom login behaviour with this plugin without the ALA-Auth plugin, set `security.oidc.enabled` to true then define a URL mapping named 'login', which can accept a single parameter `url` which is the URL to redirect to after logging in.

## Grails taglib integration with Bootstrap
This plugin borrows the taglib used in the [Grails Twitter Bootstrap plugin](https://grails.org/plugin/twitter-bootstrap) to modify the way some Core Grails tags are rendered (eg: pagination)

To enable the boostrap compatible rendering add the following line to your `Config.groovy`:

```
grails.plugins.twitterbootstrap.fixtaglib = true
```

## Changelog
* Version **4.0.0** (??)
  * Grails 4 support
  * Defaults to skin v2
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
