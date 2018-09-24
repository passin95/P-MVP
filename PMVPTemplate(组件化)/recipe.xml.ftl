<?xml version="1.0"?>
<recipe>
<#if needActivity>
    <merge from="root/AndroidManifest.xml.ftl"
           to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
</#if>

<#if needActivity && generateActivityLayout>
    <instantiate from="root/res/layout/simple.xml.ftl"
                 to="${escapeXmlAttribute(resOut)}/layout/${activityLayoutName}.xml" />
</#if>

<#if needFragment && generateFragmentLayout>
    <instantiate from="root/res/layout/simple.xml.ftl"
                 to="${escapeXmlAttribute(resOut)}/layout/${fragmentLayoutName}.xml" />
</#if>


<#if needActivity>
    <instantiate from="root/src/app_package/P-Activity.java.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(ativityPackageName)}/${pageName}Activity.java" />
    <open file="${projectOut}/src/main/java/${slashedPackageName(ativityPackageName)}/${pageName}Activity.java" />
	<open file="${projectOut}/src/main/java/${slashedPackageName(packageName)}/${moduleName}/di/${moduleName?cap_first}ActivitiesModule.java" />
	<open file="${projectOut}/src/main/java/${slashedPackageName(modulePackageName)}/PleaseCopyToProvider.java" />
</#if>

<#if needFragment>
    <instantiate from="root/src/app_package/P-Fragment.java.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(fragmentPackageName)}/${pageName}Fragment.java" />
    <open file="${projectOut}/src/main/java/${slashedPackageName(fragmentPackageName)}/${pageName}Fragment.java" />
	<open file="${projectOut}/src/main/java/${slashedPackageName(packageName)}/${moduleName}/di/${moduleName?cap_first}ActivitiesModule.java" />
	<open file="${projectOut}/src/main/java/${slashedPackageName(modulePackageName)}/PleaseCopyToProvider.java" />
</#if>

<#if needIView>
    <instantiate from="root/src/app_package/P-IView.java.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(IViewPackageName)}/${pageName}View.java" />
</#if>

<#if needPresenter>
    <instantiate from="root/src/app_package/P-Presenter.java.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(presenterPackageName)}/${pageName}Presenter.java" />
</#if>


<#if needDagger>

    <instantiate from="root/src/app_package/P-Module.java.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(modulePackageName)}/${pageName}Module.java" />

    <instantiate from="root/src/app_package/P-Contribute.java.ftl"
                   to="${projectOut}/src/main/java/${slashedPackageName(modulePackageName)}/PleaseCopyToProvider.java" />

</#if>

</recipe>
