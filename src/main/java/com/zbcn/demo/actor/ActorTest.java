package com.zbcn.demo.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.BalancingPool;

/**
 * akka是用scala编写的actor模型框架
 * url:https://blog.csdn.net/chinabestchina/article/details/73369193
 *
 * @author Administrator
 * @date 2018/10/11 16:14
 */
public class ActorTest {

    public static void main(String[] args){
        //生成角色系统
        ActorSystem sym = ActorSystem.create("msg_test");

        //生成角色 ProduceMsgActor
        ActorRef produceMsgActor = sym.actorOf(new BalancingPool(3).props(Props.create(ProduceMsgActor.class)),"ProduceMsgActor");

        //生成角色 DisposeMsgActor
        ActorRef disposeMsgActor = sym.actorOf(new BalancingPool(2).props(Props.create(DisposeMsgActor.class)), "DisposeMsgActor");

        //给produceMsgActor发消息请求
        produceMsgActor.tell("please produce msg1",ActorRef.noSender());
    }



    //定义角色 ProduceMsgActor  产生消息
    public static class ProduceMsgActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().match(String.class,t->{
                //收到消息
                System.out.println(self()+"  receive msg  from "+sender()+": "+ t);
                //响应消息请求
                Msg msg = new Msg("haha");
                System.out.println(self()+"  produce msg : "+msg.getContent());

                //根据路径查找下一个处理者
                ActorSelection nextDisposeRefs = getContext().actorSelection("/user/DisposeMsgActor");
                //将消息发给下一个处理者DisposeMsgActor
                nextDisposeRefs.tell(msg,self());

            }).matchAny(t->{
                System.out.println("no disposer");
            }).build();
        }
    }

    //定义角色 DisposeMsgActor 消费消息
    static class DisposeMsgActor extends AbstractActor{
        @Override
        public Receive createReceive() {
            return receiveBuilder().match(Msg.class,t->{
                //收到消息
                System.out.println(self()+"  receive msg  from "+sender()+": "+ t.getContent());
                System.out.println(self()+" dispose msg : "+t.getContent());
            }).matchAny(t->{
                System.out.println("no disposer");
            }).build();
        }
    }



    //定义消息
    static class Msg {
        private String  content = "apple";

        public Msg(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
