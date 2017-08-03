environments {
	/***************************** 开发环境  *****************************/
	dev {
		jdbc {
			url = 'jdbc:mysql://192.168.1.250:3306/vpyq_dev'
			user = 'vpyq_dev'
			password = 'vpyq_dev'
		}
		qrtz {
			url = 'jdbc:mysql://192.168.1.250:3306/vpyq_qrtz'
			user = 'vpyq_qrtz'
			password = 'vpyq_qrtz'
		}
		fdfs {
			tracker_server = '192.168.1.250:22122'
			http_server = 'http://192.168.1.250:1080'
		}
		redis {
			hosts = '192.168.1.250:6379'
			auth = 'zxwlpt'
			db = 3
		}
		sysconfig {
			url = 'http://127.0.0.1:19002/intf'
			appid = 'WEB_DEV'
			appkey = 'MIeS9jLc03X0w6EB3DrlEbAH8eEGe2Tu'
			encrypt = 'false'
			timeout = '5000'
			
			websign = 'true'
		}
		logo{
			url='http://localhost:14002/image/weChat/icon_wx.png'
		}
		jmx {
			enable = 'false'
			type = 'native'
			user = 'user'
			password = 'passwd'
		}
		log4j {
			sqllevel = 'debug'
			level = 'info'
		}
	}
	
	/***************************** 测试环境  *****************************/
	rm {
		jdbc {
			url = 'jdbc:mysql://192.168.1.250:3306/vpyq'
			user = 'vpyq'
			password = 'vpyq'
		}
		qrtz {
			url = 'jdbc:mysql://192.168.1.250:3306/vpyq_qrtz'
			user = 'vpyq_qrtz'
			password = 'vpyq_qrtz'
		}
		fdfs {
			tracker_server = '192.168.1.250:22122'
			http_server = 'http://192.168.1.250:1080'
		}
		redis {
			hosts = '192.168.1.250:6379'
			auth = 'zxwlpt'
			db = 3
		}
		sysconfig {
			url = 'http://127.0.0.1:15002/intf'
			appid = 'WEB_DEV'
			appkey = 'MIeS9jLc03X0w6EB3DrlEbAH8eEGe2Tu'
			encrypt = 'false'
			timeout = '10000'
			
			websign = 'true'
		}
		logo{
			url='http://192.168.1.250:10002/image/weChat/icon_wx.png'
		}
		jmx {
			enable = 'true'
			type = 'native'
			user = 'user'
			password = 'passwd'
		}
		log4j {
			sqllevel = 'debug'
			level = 'info'
		}
	}

}