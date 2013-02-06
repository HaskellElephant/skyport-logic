import java.util.concurrent.ConcurrentLinkedQueue;

public class GameThread {
    ConcurrentLinkedQueue<AIConnection> globalClients;
    int minUsers;
    int gameTimeoutSeconds;
    int roundTimeSeconds;
    GameState gamestate;
    public GameThread(ConcurrentLinkedQueue<AIConnection> globalClientsArg,
		      int minUsersArg, int gameTimeoutSecondsArg, int roundTimeSecondsArg){
	globalClients = globalClientsArg;
	minUsers = minUsersArg;
	gameTimeoutSeconds = gameTimeoutSecondsArg;
	roundTimeSeconds = roundTimeSecondsArg;
	//	gamestate = new GameState("testworld.skyportmap");
    }
    public void run(int gameSecondsTimeout){
	try {
	    Thread.sleep(500);
	}
	catch(InterruptedException e){}
	System.out.println("[GAMETHRD] waiting for " + minUsers + " users to connect");
	int waitIteration = 0;
	while(true){
	    waitIteration++;
	    try {
		Thread.sleep(500);
	    }
	    catch(InterruptedException e){}
	    // todo: prevent acceptor from accepting more clients than we want.
	    if(globalClients.size() == minUsers){
		System.out.println("[GAMETHRD] Got " + minUsers + " users, starting the round");
		break;
	    }
	}
	System.out.println("[GAMETHRD] running game for " + gameTimeoutSeconds + " seconds.");
	gameMainloop();
    }
    public void gameMainloop(){
	System.out.println("[GAMETHRD] Sending initial gamestate");
	sendGamestate();
	letClientsThink();
	letClientsThink();
	letClientsThink();
	long startTime = System.nanoTime();
	long gtsAsLong = gameTimeoutSeconds;
	while(true){
	    long roundStartTime = System.nanoTime();
	    if((roundStartTime - startTime) > gtsAsLong*1000000000){
		System.out.println("[GAMETHRD] Time over!");
		System.exit(0);
	    }
	    System.out.println("[GAMETHRD] Sending gamestate...");
	    sendGamestate();
	    letClientsThink();
	    System.out.println("[GAMETHRD] Deadline! Processing actions...");
	    // processing actions here
	}
	
    }
    public void letClientsThink(){
	try {
	    Thread.sleep(roundTimeSeconds*1000);
	}
	catch (InterruptedException e){
	    System.out.println("INTTERUPTED!");
	}
    }
    public void sendGamestate(){
	
    }
}