<h1 align="center">P-MVP</h1>


## Directory

<img src="https://github.com/passin95/P-MVP/image/module_relationship.png" width="80%" height="80%">


        - app 整个项目的最顶层，包含着所有业务Module，也是所有业务Module的跳转开关。
        - module_业务 根据业务划分Module，对于一些全局化的配置建议在app_global_config中配置，因为在Module业务设置全局配置其他Module也会生效（或覆盖app_global_config已配置好的属性），会给其它Module带来不确定性，该Module对ModuleConfig的实现的作用更应该是针对本身Module进行约束操作。
        - app_global_config app的全局配置，以及一些全局使用的类库（往往带有一些可变性）应在此依赖。
        - pmvp 可理解为Framework层，整个APP运行的根本所在，对外暴露足够的接口或方式对APP进行配置。

## Notice
- 请在local.properties下添加：


        app = true
        module_example = false
        module_fragmentation = false
        local.properties不会被add到仓库，方便协作，如果没有此需求
        
- 想更换组件化通信方式的自行按需求更换，Demo主要提供思路的参考。
- 该Demo从开始尝试（3月13日）到至今（不断的学习以及改善）时间较短，因此还有许多不足，但大概思路已在Demo中实现，剩余地方会逐步继续完善。
- [**更多组件通信方式请移步CC**](https://github.com/luckybilly/CC)
- [**更多Fragmentation的使用请点击移步**](https://github.com/YoKeyword/Fragmentation)


## Thanks
* [**MVPArms**](https://github.com/JessYanCoding/MVPArms)
* [**CC**](https://github.com/luckybilly/CC)
* [**Fragmentation**](https://github.com/YoKeyword/Fragmentation)

## License
``` 
 Copyright 2018, passin
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
