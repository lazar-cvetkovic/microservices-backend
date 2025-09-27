package com.artemi.node.entity;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="nodes")
public class NodeEntry {
  @Id private String nodeId; private String playerId; private String playfabId; private String username;
  private double score; private String country; private String city; private Instant updatedAt;
  public String getNodeId(){return nodeId;} public void setNodeId(String v){this.nodeId=v;}
  public String getPlayerId(){return playerId;} public void setPlayerId(String v){this.playerId=v;}
  public String getPlayfabId(){return playfabId;} public void setPlayfabId(String v){this.playfabId=v;}
  public String getUsername(){return username;} public void setUsername(String v){this.username=v;}
  public double getScore(){return score;} public void setScore(double v){this.score=v;}
  public String getCountry(){return country;} public void setCountry(String v){this.country=v;}
  public String getCity(){return city;} public void setCity(String v){this.city=v;}
  public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){this.updatedAt=v;}
}
