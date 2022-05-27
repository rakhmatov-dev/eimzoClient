package uz.venkon;

/*
 ***
 * Class for converting answers from json to a java object (answer)
 ***
 */
public class Answer {

    private class Certificates {
        //certkey
        public String disk;
        public String path;
        public String name;
        public String serialNumber;
        public String subjectName;
        public String validFrom;
        public String validTo;
        public String issuerName;
        public String publicKeyAlgName;
        //pfx
        public String alias;
    }


    public boolean success;
    public String [] disks;
    public String reason;
    public Certificates[] certificates;
    public String keyId;
    public String type;
    public String pkcs7_64;
    public String signature_hex;

}
