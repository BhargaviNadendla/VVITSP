import os  
if os.environ.has_key('wlsUserID'):  
    wlsUserID = os.environ['wlsUserID']  
if os.environ.has_key('wlsPassword'):  
    wlsPassword = os.environ['wlsPassword']  
connect(  url='t3://127.0.0.1:7101', adminServerName='DefaultServer')  
shutdown('DefaultServer','Server', ignoreSessions='true')  
exit()  
