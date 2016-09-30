package javaBean;

import java.sql.Timestamp;

import property.enums.enumSystem;
import property.enums.member.enumMemberState;
import property.enums.widget.enumWidgetKind;
import property.enums.widget.enumWidgetPosition;

public class DevelopedWidget extends Widget{

	private final float totalReview;
	private final int reviewCount;
	private final String subImagePath;
	private final String mainImagePath;
	private final String widgetRoot;
	
	private final String sourceRoot;
	private final int developerId;
	private final String developer;
	private final enumWidgetPosition position;
	private final String contents;
	private final  Timestamp updatedDate;
	private float version;
	
	public static class Builder{
		private float totalReview;
		private int reviewCount;
		private String subImagePath;
		private String mainImagePath;
		private String widgetRoot;
		
		private String sourceRoot;
		private float version;
		private int developerId;
		private String developer;
		private enumWidgetPosition position;
		private String contents;
		private Timestamp updatedDate;
		
		//neccessary field
		private final String wName;
		private final int wId;
		private final String kind;

		
		public Builder(int wId, String wName, String kind){
			this.wName = wName;
			this.wId = wId;
			this.kind = kind;
		}
		
		public Builder developer(String dev){
			developer = dev; return this;
		}
		public Builder developerId(int devId){
			developerId = devId; return this;
		}
		public Builder position(String pos){
			
			boolean isEmpty = true;
			for(enumWidgetPosition p : enumWidgetPosition.values()){
				if(p.getString().equals(pos)){
					isEmpty = false;
					position = p;
				}
				
			}
			if(isEmpty)
				throw new IllegalArgumentException("파라메티의 position이 서비스에 존재 하지 않는 값입니다.");
			
			return this;
		}
		public Builder contents(String con){
			contents = con;	return this;
		}
	
		public Builder widgetRoot(String root){
			widgetRoot = root;	return this;
		}
	
		public Builder updatedDate(Timestamp date){
			updatedDate = date;	return this;
		}
		public Builder version(float ver){
			version = ver;	return this;
		}
		public Builder sourceRoot(String root){
			sourceRoot = root; return this;
		}	
		
		public Builder totalReview(float p){
			totalReview = p; return this;
		}
		public Builder reviewCount(int c){
			 reviewCount = c;	return this;
			}
		public Builder subImagePath(String path){
			 subImagePath = path;	return this;
			}
		public Builder mainImagePath(String path){
			 mainImagePath = path; return this;
			}

		
		public DevelopedWidget build() throws Exception{
			return new DevelopedWidget(this);
		}
	
	
	}
	


	private DevelopedWidget(Builder b) throws Exception{
		super(b.wId, b.wName, b.kind);
		this.sourceRoot = b.sourceRoot;
		this.contents = b.contents;
		this.developer = b.developer;
		this.developerId = b.developerId;
		this.position = b.position;
		this.updatedDate = b.updatedDate;
		this.widgetRoot = b.widgetRoot;
		this.version = b.version;
		this.mainImagePath = b.mainImagePath;
		this.subImagePath = b.subImagePath;
		this.totalReview = b.totalReview;
		this.reviewCount = b.reviewCount;
		
	}



	public int getDeveloperId() {
		return developerId;
	}



	public String getDeveloper() {
		return developer;
	}



	public enumWidgetPosition getPosition() {
		return position;
	}



	public String getContents() {
		return contents;
	}



	public String getWidgetRoot() {
		return widgetRoot;
	}



	public Timestamp getUpdatedDate() {
		return updatedDate;
	}



	public float getVersion() {
		return version;
	}



	public void setVersion(float version) {
		this.version = version;
	}



	public float getTotalReview() {
		return totalReview;
	}



	public int getReviewCount() {
		return reviewCount;
	}



	public String getSubImagePath() {
		return subImagePath;
	}



	public String getMainImagePath() {
		return mainImagePath;
	}



	public String getSourceRoot() {
		return sourceRoot;
	}



	
}
