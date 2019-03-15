# coding: utf-8

from message.api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

#发邮件
import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = "xiaomasend@163.com"
authcode = "xiaoma123"
class MessageServiceHandler:
    def sendMobileMessage(self, mobile, message):
        print("sendmobile")
        return True

    def sendEmailMessage(self, email, message):
        emailmessage = MIMEText(message,"plain","utf-8")
        emailmessage["from"] = sender;
        emailmessage["to"] = email;
        emailmessage["subject"] = Header("小马邮件","utf-8")
        try:
            smtpobj = smtplib.SMTP("smtp.163.com")
            smtpobj.login(sender,authcode)
            smtpobj.sendmail(sender,[email],emailmessage.as_string())
            print("email send success")
            return True
        except:
            print("email send fail")
            return False

if __name__ == "__main__":
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    transport = TSocket.TServerSocket(host="127.0.0.1", port="9091")#host写为localhost连接不上
    tfactory = TTransport.TFramedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor,transport,tfactory,pfactory)
    print("python thrift start")
    server.serve()
    print("python thrift stop")


