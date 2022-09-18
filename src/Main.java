import jdk.jshell.spi.ExecutionControl;

import java.awt.desktop.SystemEventListener;
import java.io.*;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length > 0){
            System.out.println("Trying to read traces/" + args[0]);
            Scanner scanner = new Scanner(System.in);
            int entry = -99999;
            while(entry != -1){
                TraceReader tr = new TraceReader("../traces/" + args[0]);
                FileWriter fw = new FileWriter("result.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                System.out.println("Enter the number of the trace you want to read (0-" + (tr.getNumberTrace() - 1) + ") or -1 to leave" );
                try {
                    entry = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("Number not recognized");
                    return;
                }

                System.out.println("You entered " + entry);
                if(entry == -1) {
                    return;
                }
                if(entry > tr.getNumberTrace() - 1 || entry < 0) {
                    System.out.println("There is no trace number " + entry);
                }
                else {
                    tr.skipToTrace(entry);
                    Trace t = new Trace();
                    t.initTrace(tr);
                    t.FormTrace();
                    System.out.println(t.getTrace());
                    out.println(t.getTrace() + "\n");
                    t.setTrace("\n");
                    Ethernet E = new Ethernet(t);
                    E.readEthernet();
                    out.println(E.toString());
                    System.out.println(E);
                    if (!(E.getTypevalue().equals("0800"))) {
                        System.out.println("This Protocol is not managed by this program\nOnly IPv4 may continue\n");
                        out.println("This Protocol is not managed by this program\nOnly IPv4 may continue\n");
                        continue;
                    }
                    IP ip = new IP(t);
                    ip.readIP();
                    out.println(ip.toString());
                    System.out.println(ip);
                    if (!(ip.getProtocolName().equals("UDP"))) {
                        System.out.println("This Protocol is not managed by this program\nOnly UDP may continue\n");
                        out.println("This Protocol is not managed by this program\nOnly UDP may continue\n");
                    }
                    UDP u = new UDP(t);//Mettre dans la condition si Protocol == UDP
                    u.readUDP();
                    out.println(u.toString());
                    System.out.println(u);
                    if (!((u.getSourcePort().equals("67") && u.getDestinationPort().equals("68")) || u.getSourcePort().equals("53") || (u.getSourcePort().equals("68") && u.getDestinationPort().equals("67")) || u.getDestinationPort().equals("53"))) {
                        System.out.println("This Protocol is not managed by this program\nOnly DHCP and DNS may continue\n");
                        out.println("This Protocol is not managed by this program\nOnly DHCP and DNS may continue\n");
                    }
                    if ((u.getSourcePort().equals("67") && u.getDestinationPort().equals("68")) || (u.getSourcePort().equals("68") && u.getDestinationPort().equals("67"))) {
                        DHCP d = new DHCP(t);
                        d.readDHCP();
                        System.out.println(d);
                        out.println(d.toString());
                        Options o = new Options(t);
                        o.readOptions();
                        System.out.println(o);
                        out.println(o.toString());
                    }

                    if(u.getSourcePort().equals("53")){
                        DNS dns = new DNS(t , ip.getDNSstart());
                        dns.readDns();
                        System.out.println(dns);
                        out.println(dns);
                    }
                    System.out.println("----------------------------------------------------------------------------------");
                    out.println("----------------------------------------------------------------------------------");

                }
            }
        }
        else {
            System.out.println("Please execute with an argument: the trace placed in /traces you want to read");
        }





    }
}


