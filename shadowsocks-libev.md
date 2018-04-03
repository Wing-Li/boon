## shadowsocks-libev

#### 1. 安装
对于Ubuntu 14.04和16.04用户，请从PPA安装：

    sudo apt-get install software-properties-common -y
    sudo add-apt-repository ppa:max-c-lv/shadowsocks-libev -y
    sudo apt-get update
    sudo apt install shadowsocks-libev
    

Ubuntu 16.10或更高版本：

    sudo apt update
    sudo apt install shadowsocks-libev

    
#### 2. 配置文件：
如果只配置一个用户：

    sudo vim /etc/shadowsocks-libev/config.json
    

配置多个用户：

    sudo vim /etc/shadowsocks-libev/config-usr1.json 
    sudo vim /etc/shadowsocks-libev/config-usr2.json
    
配置的内容：

    {
        "server":"0.0.0.0",
        "server_port":8099,
        "local_port":1080,
        "password":"12345678",
        "timeout":600,
        "method":"chacha20-ietf-poly1305",
    }

    
#### 3. 开启服务

    sudo /etc/init.d/shadowsocks-libev start    # for sysvinit
    
或者
    
    sudo systemctl start shadowsocks-libev      # for systemd
    
#### 4. 在 AWS 配置

1. 进入“实例”，拖到最后，点击进入“安全组”；
2. 选择“入站”，点击“编辑”，选择“添加规则”；
3. 填入 端口号，来源 选择 任何位置，点击保存。

**至此，就配置成功了。现在去下载个客户端，填上服务器相关信息，应该就可以连接了。**

接下来是地址混淆插件，可装 可不装。



## 配置 simple-obfs 插件

#### 1. 安装(源码安装)
Debian / Ubuntu

    # Debian / Ubuntu
    sudo apt-get install --no-install-recommends build-essential autoconf libtool libssl-dev libpcre3-dev libev-dev asciidoc xmlto automake
    
    # 源码安装
    sudo git clone https://github.com/shadowsocks/simple-obfs.git
    cd simple-obfs
    sudo git submodule update --init --recursive
    sudo ./autogen.sh
    sudo ./configure && make
    sudo make install


如果 ./configure && make 命令出错，试试如下命令：
    
    ./configure --with-sodium-include=/usr/include --with-sodium-lib=/usr/lib --with-mbedtls-include=/usr/include --with-mbedtls-lib=/usr/lib
    
    
我在 ./configure && make 这一步一直出错，直到最后也没有解决。
做了各种尝试也没有成功，死活都想不起来上一次是怎么搜的了。
然后就跳过了这一步，继续之后的步骤，最后也运行成功了。
    
    
#### 2. 服务端配置
重新配置 Shadowsocks-libev 的配置文件 /etc/shadowsocks-libev/config.json
添加如下一行

    "plugin":"/usr/local/bin/obfs-server --obfs http"
    
执行如下命令，自动添加：

    ss-server -c config.json --plugin obfs-server --plugin-opts "obfs=http"
    
    
配置文件如下：

    {
        "server":"0.0.0.0",
        "server_port":8099,
        "local_port":1080,
        "password":"12345678",
        "timeout":600,
        "method":"chacha20-ietf-poly1305",
        "plugin":"/usr/local/bin/obfs-server --obfs http"
    }

    {
        "server":"0.0.0.0",
        "server_port":8338,
        "local_port":1080,
        "password":"12345678",
        "timeout":60,
        "method":"chacha20-ietf-poly1305"，
        "fast_open":true,
        "plugin":"obfs-server",
        "plugin_opts":"obfs=http"
    }

#### 3. 运行

    service shadowsocks-libev restart
    
    
## 配置客户端
以此填入：
服务器地址
服务器端口
密码
加密（选择）

插件：obfs-local
插件选项：obfs=http;obfs-host=www.bing.com  
（网址可以随便填，当访问被强网址时，会显示这个网址）
