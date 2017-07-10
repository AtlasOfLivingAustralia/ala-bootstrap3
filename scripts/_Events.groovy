eventAssetPrecompileStart = { assetConfig ->
    if(!config.grails.assets.plugin."ala-bootstrap3".excludes || config.grails.assets.plugin."ala-bootstrap3".excludes.size() == 0) {
        config.grails.assets.plugin."ala-bootstrap3".excludes = ["**/*.less"]
    }
}