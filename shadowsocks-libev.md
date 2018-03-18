## shadowsocks-libev

#### 1. 安装
对于Ubuntu 14.04和16.04用户，请从PPA安装：

    sudo apt-get install software-properties-common -y 
    sudo add-apt-repository ppa：max-c -lv / shadowsocks-libev -y 
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
        "password":"q12345678",
        "timeout":600,
        "method":"chacha20-ietf-poly1305",
    }
    
#### 3. 开启服务

    sudo /etc/init.d/shadowsocks-libev start    # for sysvinit
    
或者
    
    sudo systemctl start shadowsocks-libev      # for systemd
    
    
## 配置 simple-obfs 插件

#### 1. 安装(源码安装)
Debian / Ubuntu

    # Debian / Ubuntu
    sudo apt-get install --no-install-recommends build-essential autoconf libtool libssl-dev libpcre3-dev libev-dev asciidoc xmlto automake
    
    # 源码安装
    git clone https://github.com/shadowsocks/simple-obfs.git
    cd simple-obfs
    git submodule update --init --recursive
    ./autogen.sh
    ./configure && make
    sudo make install

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
        "password":"q12345678",
        "timeout":600,
        "method":"chacha20-ietf-poly1305",
        "plugin":"/usr/local/bin/obfs-server --obfs http"
    }

    {
        "server":"0.0.0.0",
        "server_port":8338,
        "local_port":1080,
        "password":"passwd",
        "timeout":60,
        "method":"chacha20-ietf-poly1305"，
        "fast_open":true,
        "plugin":"obfs-server",
        "plugin_opts":"obfs=http"
    }

#### 3. 运行

    service shadowsocks-libev restart