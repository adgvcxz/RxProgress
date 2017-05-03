# RxProgress


## Extension for RxJava, Track a Observable

### How to use?

```kotlin
    val indicator = ProgressIndicator()
    val dialog = ProgressDialog(this)
    
    indicator.toObservable().subscribe(dialog.isShow())
    
    findViewById(R.id.button).clicks().flatMap {
    
        //track observable. networking request etc...
        
        Observable.interval(1, TimeUnit.SECONDS).take(3).trackProgress(indicator)
    }.subscribe()
```
    	
## LICENSE

    Copyright 2017 adgvcxz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
