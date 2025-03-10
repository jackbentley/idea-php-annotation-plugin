# Changelog

## Versions
* 6.x: PhpStorm 2019.1+
* 5.x: PhpStorm 2017.1+
* 4.x: PhpStorm 2016.1.2+
* 3.x: PhpStorm 2016.1+
* 2.x: PhpStorm9, 10
* 1.x: PhpStorm8

## 6.1
* Insert import must be full fqn name (Daniel Espendiller)
* Add inspection for deprecated annotations (Cedric Ziel) [#123](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/123) [#149](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/149)

## 6.0
* Replace deprecated code usages and drop support for old PhpStorm versions (Daniel Espendiller) [#148](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/148)
* Add IntelliJ plugin icon (Daniel Espendiller) [#145](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/145)
* WI-46553 Register PhpDocIdentifierReference on doc token instead of registering it on parent doc tag and adjusting the range (Kirill Smelov) [#143](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/143)

## 5.3
* Support latest EAP version [#107](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/107) [#105](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/105) @vkhramtsov
* Add new api to access class constants psi element in PhpDocTag properties

## 5.2.1
* Add extension point: Create use alias setting from third party plugin [#99](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/99) [#97](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/97)
* Add alias for Swagger-PHP [#96](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/96) @derrabus

## 5.2
* API: add annotation util to access property and default values of PhpDocTag attribute list
* Move Doctrine repositoryClass class check to inspection and provide and help message to Symfony documentation
* Drop PluginUtil.isEnabled checks
* Drop old api workarounds for annotation @Targets and support some more edge cases in property extraction
* Provide Annotation class usage linemarker [#79](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/79)
* API: Provide an index with annotated elements stubs [#53](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/53)
* Add Doctrine @Embedded class generator [#88](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/88) and refactoring of orm generator
* Drop old workarounds for old PhpStorm in getDocBlockTag completion pattern
* Drop workaround for class interface fqn class name of old PhpStorm
* Update string value resolving to support class constants
* Migration annotation import annotator to inspection; prevent memory leaks; use visitor pattern and provider better inspection overlay for multiple and single class
* Prevent possible memory leaks issue in Doctrine repositoryClass quickfix

## 5.1
* Allow multiple PhpTypes declaration for bool type detection
* Add PhpStorm 2017.2 travis environment
* Fix npe in Doctrine repository annotator [#83](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/83)
* Replace deprecation usage of annotation indexer externalizer
* Imported interfaces @Query(FooInterface::class) are marked as unused when used for constant [#82](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/82)

## 5.0
* PhpStorm 2017.1 build
* Add FOSRest alias [#87](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/87) @Koc

## 4.3.2
* Replace deprecated api usages

## 4.3.1
* Add virtual annotation classes properties / fields [#80](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/80)

## 4.3
* Index issue on YAML File; drop usage of DefaultFileTypeSpecificInputFilter [#72](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/72)
* Add autosuggest and ctrl+click for Doctrine CustomIdGenerator [#48](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/48)
* Add extension point to register global namespace prefixes [#81](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/81)

## 4.2
* Add alias for VichUploadableBundle [#69](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/69) @Koc
* Add references for Doctrine @ORM\Embedded.class [#68](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/68)
* Add autocomplete for array values of properties as extension point [#62](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/62)
* Add Symfony internal route array completion provider [#62](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/62)
* Add PHP-Toolbox provider for new array annotations as "annotation_array" [#62](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/62)

## 4.1.2
* PhpStorm 2016.3: Switch from PhpResolveResult#create to PsiElementResolveResult#createResults [#66](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/66)

## 4.1.1
* Fix class cast issue for reference contributor [#64](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/64)

## 4.1
* Add class constant support for import optimization and provide references. [#22](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/22), [#26](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/26), [#38](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/38), [#40](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/40)
* Implement workaround for class constant usage in doc array [WI-32801](https://youtrack.jetbrains.com/issue/WI-32801)

## 4.0.1
* Fails to recognize import useful for PHPDoc Annotation(s), PHPStorm 2016.2 [#59](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/59), [#63](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/63) @artspb

## 4.0
* Support nested annotations [#8](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/8), [#55](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/55)
* Add button in settings form to force reindex of annotation classes [#55](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/55)
* Drop project references in settings form; prevent memory leaks
* Java8 migration
* Use newest api level and migrate internal code usage

## 3.0.1
* Custom use aliases do not work after restarting the IDE [#54](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/54)

## 3.0
* Change minimal api level to PhpStorm 2016.1
* Replace deprecated PhpUse usages [#52](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/52) @artspb
* Add application level settings to set auto-complete without "()" [#42](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/42)
* Add option to always use an aliased import for some annotations or namespaces [#50](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/50)
* Doctrine orm class initialize should add an use alias
* Fix inline annotations are not recognized [#24](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/24)
* Replace deprecated PhpStorm method calls [#31](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/31)
* Doctrine repository creation intention is now available in attribute value

## 2.6.2
* Add PHP Toolbox support [#49](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/49)
* Implement integration tests for main plugin functionality

## 2.6.1

* Replace Doctrine static column types with parser to support simple_array and json_array [#39](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/39)
* On ORM annotations generation missing ORM alias will add automatically

## 2.6 / 1.6
* Add Doctrine repository create quickfix
* Add intention creation for Doctrine: @Column properties
* Add generator for Doctrine entity class and properties

## 1.5.1
* fix "missing import" inspection highlights all doc blocks #25

## 1.5
* Fix property value pattern completion for PhpStorm8
* Add whitelist for annotation PhpDoc on next siblings, to filter inline doc blocks [#24](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/24)
* Move "Missing Import" annotator to inspection [#19](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/19)

## 1.4
* API: add new proxy method "getPropertyValue" to get representing psielements
* Completion and goto for class constants inside doctags [#18](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/18)
* Annotation are now valid in namespace less files; this also brings some performance improvements because of lower search scope

## 1.3
* Typo fix that class completion are detected as method
* Attach annotation insertHandler for alias completion
* Rename getRootValue to more naming getDefaultPropertyValue
* Remove unused configuration form [#15](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/15)
* Fix that docblock completion confidence is valid in comments and provides autopopup

## 1.2
* Fix annotation class inside composer libraries where not autocompleted [#13](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/13)
* Support fqn classes annotation [#17](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/17)
* Add java annotations for all extension points
* Pipe additional util methods in extension parameters

## 1.1
* Add doc tag property value utils and dicts
* Add alias annotation class completion
* Improve performance on annotation class completion
* Use php class statement scope instead of file scope for namespace collection

## 1.0.1
* Detect annotation classes in same namespace

## 1.0
* Remove all PhpStorm6 hacks, support new Api and only allow PhpStorm7 builds
* Add more property value type detections
* Add and change extension points to reflect latest api features
* Add class import annotator
* Add docblock property value provider for @Enum
* Add Doctrine providers for: targetEntity, repositoryClass, mappedBy, inversedBy
* Migrate pattern to allow multiple docblocks [#12](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/12), [#8](https://github.com/Haehnchen/idea-php-annotation-plugin/issues/8)
* Plugin dont need to explicit enable

## 0.4
* Only support PhpStorm > 7
* Activate annotation class reference provider and use it where possible

## 0.3
* Pattern fix to support eap 131.235
* Support "@" completion WI-20265
* Optimize doc tag name insert handler

## 0.2
* Better property completion pattern and type detection
* Optimize property value insert handler
* Extension points for property goto and completion eg @Template("file.html.twig")

## 0.1
* Init version support PhpStorm6 and 7