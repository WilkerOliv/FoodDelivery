package br.com.fooddelivery.catalog;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="store_cover_submissions")
public class StoreCoverSubmission {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="store_id") private Store store;
    @Column(name="file_name",nullable=false,length=80,unique=true) private String fileName;
    @Column(nullable=false,length=16) private String status="PENDING";
    @Column(name="review_note",length=500) private String reviewNote;
    @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt=Instant.now();
    private Instant reviewedAt;
    private UUID reviewedBy;
    @Version private long version;
    protected StoreCoverSubmission() {}
    public StoreCoverSubmission(Store store,String fileName){this.store=store;this.fileName=fileName;}
    public void supersede(){requirePending();status="SUPERSEDED";}
    public void review(boolean approved,String note,UUID reviewer){
        requirePending();
        if(!approved&&(note==null||note.isBlank()))throw new IllegalArgumentException("Informe o motivo para o vendedor corrigir a imagem.");
        status=approved?"APPROVED":"REJECTED";reviewNote=note==null||note.isBlank()?null:note.trim();reviewedAt=Instant.now();reviewedBy=reviewer;
    }
    private void requirePending(){if(!"PENDING".equals(status))throw new IllegalStateException("Esta imagem não está mais aguardando aprovação. Atualize a lista.");}
    public UUID getId(){return id;}
    public Store getStore(){return store;}
    public String getFileName(){return fileName;}
    public String getStatus(){return status;}
    public String getReviewNote(){return reviewNote;}
    public Instant getCreatedAt(){return createdAt;}
    public Instant getReviewedAt(){return reviewedAt;}
}
