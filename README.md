# 此项目仿照[Logger](https://github.com/orhanobut/logger)编写，目的是方便在Harmony上，输出整洁漂亮的打印信息。

关于LICENSE我不太懂，如果有问题，请及时联系我，我会及时修改。

暂时只支持json不支持xml。

因为现在HiLog的DEBUG级别打印，限制必须开启usb调试，而使用华为的远程模拟器开启usb调试会死机～，所以json格式的打印增加了级别选择的支持。


### HiLogger
Simple, pretty and powerful logger for harmony

### Setup
Download
```groovy
implementation 'com.github.boxuanjia:hilogger:1.0.1'
```

Initialize
```java
HiLogger.addLogAdapter(new HarmonyLogAdapter());
```
And use
```java
HiLogger.d("hello");
```

### Options
```java
HiLogger.d("debug");
HiLogger.e("error");
HiLogger.w("warning");
HiLogger.i("information");
HiLogger.wtf("What a Terrible Failure");
```

String format arguments are supported
```java
HiLogger.d("hello %s", "world");
```

Collections are supported (only available for debug logs)
```java
HiLogger.d(MAP);
HiLogger.d(SET);
HiLogger.d(LIST);
HiLogger.d(ARRAY);
```

Json support (output will be in debug level)
```java
HiLogger.json(JSON_CONTENT);
```

### Advanced
```java
FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
  .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
  .methodCount(0)         // (Optional) How many method line to show. Default 2
  .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
  .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default HiLog
  .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_HI_LOGGER
  .build();

HiLogger.addLogAdapter(new HarmonyLogAdapter(formatStrategy));
```

### Loggable
Log adapter checks whether the log should be printed or not by checking this function.
If you want to disable/hide logs for output, override `isLoggable` method. 
`true` will print the log message, `false` will ignore it.
```java
HiLogger.addLogAdapter(new HarmonyLogAdapter() {
  @Override public boolean isLoggable(int priority, int domain, String tag) {
    return BuildConfig.DEBUG;
  }
});
```

### Save logs to the file
```java
HiLogger.addLogAdapter(new DiskLogAdapter());
```

Add custom tag to Csv format strategy
```java
FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
  .tag("custom")
  .build();
  
HiLogger.addLogAdapter(new DiskLogAdapter(formatStrategy));
```

### License
<pre>
Copyright 2020 boxuanjia

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
