package com.demo.android.newlife.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XiongRun on 2017/5/13.
 */

public class Livings implements Serializable{

	private int dm_error;
	private String error_msg;
	private int expire_time;
	private List<LivesBean> lives;

	public int getDm_error() {
		return dm_error;
	}

	public void setDm_error(int dm_error) {
		this.dm_error = dm_error;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public int getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(int expire_time) {
		this.expire_time = expire_time;
	}

	public List<LivesBean> getLives() {
		return lives;
	}

	public void setLives(List<LivesBean> lives) {
		this.lives = lives;
	}

	public static class LivesBean implements Serializable{

		private CreatorBean creator;
		private String id;
		private String name;
		private String city;
		private String share_addr;
		private String stream_addr;
		private int version;
		private int slot;
		private String live_type;
		private int landscape;
		private int optimal;
		private int group;
		private String distance;
		private int link;
		private int multi;
		private int rotate;
		private ExtraBean extra;
		private int tag_id;
		private List<?> like;

		public CreatorBean getCreator() {
			return creator;
		}

		public void setCreator(CreatorBean creator) {
			this.creator = creator;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getShare_addr() {
			return share_addr;
		}

		public void setShare_addr(String share_addr) {
			this.share_addr = share_addr;
		}

		public String getStream_addr() {
			return stream_addr;
		}

		public void setStream_addr(String stream_addr) {
			this.stream_addr = stream_addr;
		}

		public int getVersion() {
			return version;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public int getSlot() {
			return slot;
		}

		public void setSlot(int slot) {
			this.slot = slot;
		}

		public String getLive_type() {
			return live_type;
		}

		public void setLive_type(String live_type) {
			this.live_type = live_type;
		}

		public int getLandscape() {
			return landscape;
		}

		public void setLandscape(int landscape) {
			this.landscape = landscape;
		}

		public int getOptimal() {
			return optimal;
		}

		public void setOptimal(int optimal) {
			this.optimal = optimal;
		}

		public int getGroup() {
			return group;
		}

		public void setGroup(int group) {
			this.group = group;
		}

		public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public int getLink() {
			return link;
		}

		public void setLink(int link) {
			this.link = link;
		}

		public int getMulti() {
			return multi;
		}

		public void setMulti(int multi) {
			this.multi = multi;
		}

		public int getRotate() {
			return rotate;
		}

		public void setRotate(int rotate) {
			this.rotate = rotate;
		}

		public ExtraBean getExtra() {
			return extra;
		}

		public void setExtra(ExtraBean extra) {
			this.extra = extra;
		}

		public int getTag_id() {
			return tag_id;
		}

		public void setTag_id(int tag_id) {
			this.tag_id = tag_id;
		}

		public List<?> getLike() {
			return like;
		}

		public void setLike(List<?> like) {
			this.like = like;
		}

		public static class CreatorBean {
			/**
			 * id : 2655986
			 * level : 10
			 * gender : 0
			 * nick : 鑺辨娆ｐ煍�
			 * portrait : MTQ3MjU4MDk0Mzk1OSM2MDYjanBn.jpg
			 */

			private int id;
			private int level;
			private int gender;
			private String nick;
			private String portrait;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public int getLevel() {
				return level;
			}

			public void setLevel(int level) {
				this.level = level;
			}

			public int getGender() {
				return gender;
			}

			public void setGender(int gender) {
				this.gender = gender;
			}

			public String getNick() {
				return nick;
			}

			public void setNick(String nick) {
				this.nick = nick;
			}

			public String getPortrait() {
				return portrait;
			}

			public void setPortrait(String portrait) {
				this.portrait = portrait;
			}
		}

		public static class ExtraBean {
			/**
			 * cover : null
			 * label : [{"tab_name":"娲绘臣寮\u20ac鏈�","tab_key":"娲绘臣寮\u20ac鏈�","cl":[0,216,201,1]},{"tab_name":"濂藉０闊�","tab_key":"濂藉０闊�","cl":[0,216,201,1]},{"tab_name":"鍖椾含甯�","tab_key":"鍖椾含甯�","cl":[0,216,201,1]}]
			 */

			private Object cover;
			private List<LabelBean> label;

			public Object getCover() {
				return cover;
			}

			public void setCover(Object cover) {
				this.cover = cover;
			}

			public List<LabelBean> getLabel() {
				return label;
			}

			public void setLabel(List<LabelBean> label) {
				this.label = label;
			}

			public static class LabelBean {
				/**
				 * tab_name : 娲绘臣寮€鏈�
				 * tab_key : 娲绘臣寮€鏈�
				 * cl : [0,216,201,1]
				 */

				private String tab_name;
				private String tab_key;
				private List<Integer> cl;

				public String getTab_name() {
					return tab_name;
				}

				public void setTab_name(String tab_name) {
					this.tab_name = tab_name;
				}

				public String getTab_key() {
					return tab_key;
				}

				public void setTab_key(String tab_key) {
					this.tab_key = tab_key;
				}

				public List<Integer> getCl() {
					return cl;
				}

				public void setCl(List<Integer> cl) {
					this.cl = cl;
				}
			}
		}
	}
}
