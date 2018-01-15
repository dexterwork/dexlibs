# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/minghongyu/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.code.gson.** { *; }
-dontwarn com.google.code.gson.**
-keep class eu.the4thfloor.volley.** { *; }
-dontwarn eu.the4thfloor.volley.**


# Application classes that will be serialized/deserialized over Gson
-keep class tw.net.pic.mobi.data.gsonobj.** { *; }

# facebook SDK
-keep class com.facebook.** { *; }

# recyclerviewpager
-keep class com.lsjwzh.widget.recyclerviewpager.**
-dontwarn com.lsjwzh.widget.recyclerviewpager.**

# dropbox api - OkHttp and Servlet optional dependencies
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.google.appengine.**
-dontwarn javax.servlet.**

# Support classes for compatibility with older API versions
-dontwarn android.support.**
-dontnote android.support.**

-dontwarn org.xmlpull.v1.**

# Suppress warnings on sun.misc.Unsafe
-dontnote sun.misc.Unsafe
-dontwarn sun.misc.Unsafe

# ProGuard Configuration file
# see https://github.com/google/google-api-java-client-samples/blob/master/tasks-android-sample/proguard-google-api-client.txt
#
# See http://proguard.sourceforge.net/index.html#manual/usage.html

# Needed to keep generic types and @Key annotations accessed via reflection
-keepclassmembers class * {
  @com.google.api.client.util.Key <fields>;
}

# Needed by google-http-client-android when linking against an older platform version

-dontwarn com.google.api.client.extensions.android.**

# Needed by google-api-client-android when linking against an older platform version

-dontwarn com.google.api.client.googleapis.extensions.android.**

# Needed by google-play-services when linking against an older platform version

-dontwarn com.google.android.gms.**

# com.google.client.util.IOUtils references java.nio.file.Files when on Java 7+
-dontnote java.nio.file.Files, java.nio.file.Path

# Suppress notes on LicensingServices
-dontnote **.ILicensingService

#Glide
-keepnames class tw.net.pic.mobi.tool.GlideExtraModule
# or more generally:
-keep public class * implements com.bumptech.glide.module.GlideModule

# [2017/04/15] jason: (It says "for DexGuard only", just remove the line if you use ProGuard.)
# for DexGuard only
# -keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# for module: OPPreMemberApi
# Retrofit2
# http://square.github.io/retrofit/
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
# https://github.com/square/retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.apache.http.**
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**
-keep class twgtv4_ot.entity.** {*;}
-keep class object.** {*;}
-dontwarn twgtv4_ot.entity.**
-dontwarn object.**

-keep class org.jbundle.util.osgi.** {*;}
-keep interface org.jbundle.util.osgi.** {*;}
-dontwarn org.jbundle.util.osgi.**

-keep class eu.the4thfloor.volley.** { *; }
-dontwarn eu.the4thfloor.volley.**
-ignorewarnings
-keep class * {
    public private *;
}