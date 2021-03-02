package com.baofu.netlib.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigModelBean {

    @SerializedName("config")
    private ConfigBean _$Config139; // FIXME check this code
    private EmojiBean emoji;
    private String updated_at;
    private List<ModulesBean> modules;
    private List<?> localpush;
    private List<TemplateBean> template;

    public ConfigBean get_$Config139() {
        return _$Config139;
    }

    public void set_$Config139(ConfigBean _$Config139) {
        this._$Config139 = _$Config139;
    }

    public EmojiBean getEmoji() {
        return emoji;
    }

    public void setEmoji(EmojiBean emoji) {
        this.emoji = emoji;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<ModulesBean> getModules() {
        return modules;
    }

    public void setModules(List<ModulesBean> modules) {
        this.modules = modules;
    }

    public List<?> getLocalpush() {
        return localpush;
    }

    public void setLocalpush(List<?> localpush) {
        this.localpush = localpush;
    }

    public List<TemplateBean> getTemplate() {
        return template;
    }

    public void setTemplate(List<TemplateBean> template) {
        this.template = template;
    }

    public static class ConfigBean {
        @SerializedName("gif_max_size")
        private int _$Gif_max_size196; // FIXME check this code
        private int max_upload_files;
        private int match_refresh_interval;
        private boolean qr_login;
        private int max_upload_files_size;
        private String max_upload_files_size_tips;
        private int total_max_upload_files_size;
        private String total_max_upload_files_size_tips;
        private String sidebar_bg_image;
        private String share_logo_image;
        private String rename_card_scheme;
        private boolean live_entrance_switch;
        private boolean article_preload;
        private Object my_favorite;
        private int dhouse_cache_page_num;
        private int dhouse_cache_maxtime;
        private int close_hometab_record;
        private int notification_interval_time;
        private boolean in_player;
        private int chat_connect_timeout;
        private PlayerBean player;
        private boolean reviewing;
        private StoryBean story;
        private LaunchImageBean launch_image;
        private ShareBean share;
        private HotBean hot;
        private int notify_rate;
        private String default_scheme;
        private int show_guide_img;
        private int show_gamecenter;
        private String gamecenter_url;
        private int show_video_upload;
        private int show_video_parse;
        private String archive_detail_host;
        private int group_video_upload;
        private int show_score;
        private String score_update_time;
        private String about_scheme;
        private int show_minitop_video_upload;
        private int show_minitop_video_parse;
        private String video_parse_text;
        private String video_parse_link;
        private int free_flow_open;
        private int bullet_screen_num;
        private int show_bullet_screen;
        private int start_image_duration;
        private String dqh_application_scheme;
        private String up_image;
        private String up_image_high;
        private int dsite_switch;
        private String gamecenter_scheme;
        private int video_ratio;
        private String score_goals_sound;
        private String score_red_sound;
        private int match_sign;
        private Object footer_icons;
        private int ad_title_min_len;
        private int hot_comment_limit_num;
        private int upload_video_max_length;
        private int show_task_entrance;
        private String d_station_task_url;
        private int match_countdown_time;
        private ChatImageBean chat_image;
        private String d_coin_name;
        private String d_coin_h5_scheme;
        private int is_show_pp_sports;
        private AdFeedbackBean ad_feedback;
        private int auto_play_video;
        private List<String> url_black_list;
        private List<String> notification_strategy_list;
        private List<String> url_network_check_list;
        private List<String> ad_emoji;
        private List<TagsBean> tags;
        private List<String> domain_ua_white_list;

        public int get_$Gif_max_size196() {
            return _$Gif_max_size196;
        }

        public void set_$Gif_max_size196(int _$Gif_max_size196) {
            this._$Gif_max_size196 = _$Gif_max_size196;
        }

        public int getMax_upload_files() {
            return max_upload_files;
        }

        public void setMax_upload_files(int max_upload_files) {
            this.max_upload_files = max_upload_files;
        }

        public int getMatch_refresh_interval() {
            return match_refresh_interval;
        }

        public void setMatch_refresh_interval(int match_refresh_interval) {
            this.match_refresh_interval = match_refresh_interval;
        }

        public boolean isQr_login() {
            return qr_login;
        }

        public void setQr_login(boolean qr_login) {
            this.qr_login = qr_login;
        }

        public int getMax_upload_files_size() {
            return max_upload_files_size;
        }

        public void setMax_upload_files_size(int max_upload_files_size) {
            this.max_upload_files_size = max_upload_files_size;
        }

        public String getMax_upload_files_size_tips() {
            return max_upload_files_size_tips;
        }

        public void setMax_upload_files_size_tips(String max_upload_files_size_tips) {
            this.max_upload_files_size_tips = max_upload_files_size_tips;
        }

        public int getTotal_max_upload_files_size() {
            return total_max_upload_files_size;
        }

        public void setTotal_max_upload_files_size(int total_max_upload_files_size) {
            this.total_max_upload_files_size = total_max_upload_files_size;
        }

        public String getTotal_max_upload_files_size_tips() {
            return total_max_upload_files_size_tips;
        }

        public void setTotal_max_upload_files_size_tips(String total_max_upload_files_size_tips) {
            this.total_max_upload_files_size_tips = total_max_upload_files_size_tips;
        }

        public String getSidebar_bg_image() {
            return sidebar_bg_image;
        }

        public void setSidebar_bg_image(String sidebar_bg_image) {
            this.sidebar_bg_image = sidebar_bg_image;
        }

        public String getShare_logo_image() {
            return share_logo_image;
        }

        public void setShare_logo_image(String share_logo_image) {
            this.share_logo_image = share_logo_image;
        }

        public String getRename_card_scheme() {
            return rename_card_scheme;
        }

        public void setRename_card_scheme(String rename_card_scheme) {
            this.rename_card_scheme = rename_card_scheme;
        }

        public boolean isLive_entrance_switch() {
            return live_entrance_switch;
        }

        public void setLive_entrance_switch(boolean live_entrance_switch) {
            this.live_entrance_switch = live_entrance_switch;
        }

        public boolean isArticle_preload() {
            return article_preload;
        }

        public void setArticle_preload(boolean article_preload) {
            this.article_preload = article_preload;
        }

        public Object getMy_favorite() {
            return my_favorite;
        }

        public void setMy_favorite(Object my_favorite) {
            this.my_favorite = my_favorite;
        }

        public int getDhouse_cache_page_num() {
            return dhouse_cache_page_num;
        }

        public void setDhouse_cache_page_num(int dhouse_cache_page_num) {
            this.dhouse_cache_page_num = dhouse_cache_page_num;
        }

        public int getDhouse_cache_maxtime() {
            return dhouse_cache_maxtime;
        }

        public void setDhouse_cache_maxtime(int dhouse_cache_maxtime) {
            this.dhouse_cache_maxtime = dhouse_cache_maxtime;
        }

        public int getClose_hometab_record() {
            return close_hometab_record;
        }

        public void setClose_hometab_record(int close_hometab_record) {
            this.close_hometab_record = close_hometab_record;
        }

        public int getNotification_interval_time() {
            return notification_interval_time;
        }

        public void setNotification_interval_time(int notification_interval_time) {
            this.notification_interval_time = notification_interval_time;
        }

        public boolean isIn_player() {
            return in_player;
        }

        public void setIn_player(boolean in_player) {
            this.in_player = in_player;
        }

        public int getChat_connect_timeout() {
            return chat_connect_timeout;
        }

        public void setChat_connect_timeout(int chat_connect_timeout) {
            this.chat_connect_timeout = chat_connect_timeout;
        }

        public PlayerBean getPlayer() {
            return player;
        }

        public void setPlayer(PlayerBean player) {
            this.player = player;
        }

        public boolean isReviewing() {
            return reviewing;
        }

        public void setReviewing(boolean reviewing) {
            this.reviewing = reviewing;
        }

        public StoryBean getStory() {
            return story;
        }

        public void setStory(StoryBean story) {
            this.story = story;
        }

        public LaunchImageBean getLaunch_image() {
            return launch_image;
        }

        public void setLaunch_image(LaunchImageBean launch_image) {
            this.launch_image = launch_image;
        }

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public HotBean getHot() {
            return hot;
        }

        public void setHot(HotBean hot) {
            this.hot = hot;
        }

        public int getNotify_rate() {
            return notify_rate;
        }

        public void setNotify_rate(int notify_rate) {
            this.notify_rate = notify_rate;
        }

        public String getDefault_scheme() {
            return default_scheme;
        }

        public void setDefault_scheme(String default_scheme) {
            this.default_scheme = default_scheme;
        }

        public int getShow_guide_img() {
            return show_guide_img;
        }

        public void setShow_guide_img(int show_guide_img) {
            this.show_guide_img = show_guide_img;
        }

        public int getShow_gamecenter() {
            return show_gamecenter;
        }

        public void setShow_gamecenter(int show_gamecenter) {
            this.show_gamecenter = show_gamecenter;
        }

        public String getGamecenter_url() {
            return gamecenter_url;
        }

        public void setGamecenter_url(String gamecenter_url) {
            this.gamecenter_url = gamecenter_url;
        }

        public int getShow_video_upload() {
            return show_video_upload;
        }

        public void setShow_video_upload(int show_video_upload) {
            this.show_video_upload = show_video_upload;
        }

        public int getShow_video_parse() {
            return show_video_parse;
        }

        public void setShow_video_parse(int show_video_parse) {
            this.show_video_parse = show_video_parse;
        }

        public String getArchive_detail_host() {
            return archive_detail_host;
        }

        public void setArchive_detail_host(String archive_detail_host) {
            this.archive_detail_host = archive_detail_host;
        }

        public int getGroup_video_upload() {
            return group_video_upload;
        }

        public void setGroup_video_upload(int group_video_upload) {
            this.group_video_upload = group_video_upload;
        }

        public int getShow_score() {
            return show_score;
        }

        public void setShow_score(int show_score) {
            this.show_score = show_score;
        }

        public String getScore_update_time() {
            return score_update_time;
        }

        public void setScore_update_time(String score_update_time) {
            this.score_update_time = score_update_time;
        }

        public String getAbout_scheme() {
            return about_scheme;
        }

        public void setAbout_scheme(String about_scheme) {
            this.about_scheme = about_scheme;
        }

        public int getShow_minitop_video_upload() {
            return show_minitop_video_upload;
        }

        public void setShow_minitop_video_upload(int show_minitop_video_upload) {
            this.show_minitop_video_upload = show_minitop_video_upload;
        }

        public int getShow_minitop_video_parse() {
            return show_minitop_video_parse;
        }

        public void setShow_minitop_video_parse(int show_minitop_video_parse) {
            this.show_minitop_video_parse = show_minitop_video_parse;
        }

        public String getVideo_parse_text() {
            return video_parse_text;
        }

        public void setVideo_parse_text(String video_parse_text) {
            this.video_parse_text = video_parse_text;
        }

        public String getVideo_parse_link() {
            return video_parse_link;
        }

        public void setVideo_parse_link(String video_parse_link) {
            this.video_parse_link = video_parse_link;
        }

        public int getFree_flow_open() {
            return free_flow_open;
        }

        public void setFree_flow_open(int free_flow_open) {
            this.free_flow_open = free_flow_open;
        }

        public int getBullet_screen_num() {
            return bullet_screen_num;
        }

        public void setBullet_screen_num(int bullet_screen_num) {
            this.bullet_screen_num = bullet_screen_num;
        }

        public int getShow_bullet_screen() {
            return show_bullet_screen;
        }

        public void setShow_bullet_screen(int show_bullet_screen) {
            this.show_bullet_screen = show_bullet_screen;
        }

        public int getStart_image_duration() {
            return start_image_duration;
        }

        public void setStart_image_duration(int start_image_duration) {
            this.start_image_duration = start_image_duration;
        }

        public String getDqh_application_scheme() {
            return dqh_application_scheme;
        }

        public void setDqh_application_scheme(String dqh_application_scheme) {
            this.dqh_application_scheme = dqh_application_scheme;
        }

        public String getUp_image() {
            return up_image;
        }

        public void setUp_image(String up_image) {
            this.up_image = up_image;
        }

        public String getUp_image_high() {
            return up_image_high;
        }

        public void setUp_image_high(String up_image_high) {
            this.up_image_high = up_image_high;
        }

        public int getDsite_switch() {
            return dsite_switch;
        }

        public void setDsite_switch(int dsite_switch) {
            this.dsite_switch = dsite_switch;
        }

        public String getGamecenter_scheme() {
            return gamecenter_scheme;
        }

        public void setGamecenter_scheme(String gamecenter_scheme) {
            this.gamecenter_scheme = gamecenter_scheme;
        }

        public int getVideo_ratio() {
            return video_ratio;
        }

        public void setVideo_ratio(int video_ratio) {
            this.video_ratio = video_ratio;
        }

        public String getScore_goals_sound() {
            return score_goals_sound;
        }

        public void setScore_goals_sound(String score_goals_sound) {
            this.score_goals_sound = score_goals_sound;
        }

        public String getScore_red_sound() {
            return score_red_sound;
        }

        public void setScore_red_sound(String score_red_sound) {
            this.score_red_sound = score_red_sound;
        }

        public int getMatch_sign() {
            return match_sign;
        }

        public void setMatch_sign(int match_sign) {
            this.match_sign = match_sign;
        }

        public Object getFooter_icons() {
            return footer_icons;
        }

        public void setFooter_icons(Object footer_icons) {
            this.footer_icons = footer_icons;
        }

        public int getAd_title_min_len() {
            return ad_title_min_len;
        }

        public void setAd_title_min_len(int ad_title_min_len) {
            this.ad_title_min_len = ad_title_min_len;
        }

        public int getHot_comment_limit_num() {
            return hot_comment_limit_num;
        }

        public void setHot_comment_limit_num(int hot_comment_limit_num) {
            this.hot_comment_limit_num = hot_comment_limit_num;
        }

        public int getUpload_video_max_length() {
            return upload_video_max_length;
        }

        public void setUpload_video_max_length(int upload_video_max_length) {
            this.upload_video_max_length = upload_video_max_length;
        }

        public int getShow_task_entrance() {
            return show_task_entrance;
        }

        public void setShow_task_entrance(int show_task_entrance) {
            this.show_task_entrance = show_task_entrance;
        }

        public String getD_station_task_url() {
            return d_station_task_url;
        }

        public void setD_station_task_url(String d_station_task_url) {
            this.d_station_task_url = d_station_task_url;
        }

        public int getMatch_countdown_time() {
            return match_countdown_time;
        }

        public void setMatch_countdown_time(int match_countdown_time) {
            this.match_countdown_time = match_countdown_time;
        }

        public ChatImageBean getChat_image() {
            return chat_image;
        }

        public void setChat_image(ChatImageBean chat_image) {
            this.chat_image = chat_image;
        }

        public String getD_coin_name() {
            return d_coin_name;
        }

        public void setD_coin_name(String d_coin_name) {
            this.d_coin_name = d_coin_name;
        }

        public String getD_coin_h5_scheme() {
            return d_coin_h5_scheme;
        }

        public void setD_coin_h5_scheme(String d_coin_h5_scheme) {
            this.d_coin_h5_scheme = d_coin_h5_scheme;
        }

        public int getIs_show_pp_sports() {
            return is_show_pp_sports;
        }

        public void setIs_show_pp_sports(int is_show_pp_sports) {
            this.is_show_pp_sports = is_show_pp_sports;
        }

        public AdFeedbackBean getAd_feedback() {
            return ad_feedback;
        }

        public void setAd_feedback(AdFeedbackBean ad_feedback) {
            this.ad_feedback = ad_feedback;
        }

        public int getAuto_play_video() {
            return auto_play_video;
        }

        public void setAuto_play_video(int auto_play_video) {
            this.auto_play_video = auto_play_video;
        }

        public List<String> getUrl_black_list() {
            return url_black_list;
        }

        public void setUrl_black_list(List<String> url_black_list) {
            this.url_black_list = url_black_list;
        }

        public List<String> getNotification_strategy_list() {
            return notification_strategy_list;
        }

        public void setNotification_strategy_list(List<String> notification_strategy_list) {
            this.notification_strategy_list = notification_strategy_list;
        }

        public List<String> getUrl_network_check_list() {
            return url_network_check_list;
        }

        public void setUrl_network_check_list(List<String> url_network_check_list) {
            this.url_network_check_list = url_network_check_list;
        }

        public List<String> getAd_emoji() {
            return ad_emoji;
        }

        public void setAd_emoji(List<String> ad_emoji) {
            this.ad_emoji = ad_emoji;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<String> getDomain_ua_white_list() {
            return domain_ua_white_list;
        }

        public void setDomain_ua_white_list(List<String> domain_ua_white_list) {
            this.domain_ua_white_list = domain_ua_white_list;
        }

        public static class PlayerBean {
            @SerializedName("video")
            private String _$Video238; // FIXME check this code
            private String ad;
            private String live;
            private String url;
            private String key;

            public String get_$Video238() {
                return _$Video238;
            }

            public void set_$Video238(String _$Video238) {
                this._$Video238 = _$Video238;
            }

            public String getAd() {
                return ad;
            }

            public void setAd(String ad) {
                this.ad = ad;
            }

            public String getLive() {
                return live;
            }

            public void setLive(String live) {
                this.live = live;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class StoryBean {
            /**
             * text : 我的懂球帝故事
             * url : dongqiudi:///news/56341
             * display : false
             */

            private String text;
            private String url;
            private boolean display;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public boolean isDisplay() {
                return display;
            }

            public void setDisplay(boolean display) {
                this.display = display;
            }
        }

        public static class LaunchImageBean {
            /**
             * img_url :
             * redirect_url :
             * duration : 3
             */

            private String img_url;
            private String redirect_url;
            private int duration;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getRedirect_url() {
                return redirect_url;
            }

            public void setRedirect_url(String redirect_url) {
                this.redirect_url = redirect_url;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }
        }

        public static class ShareBean {
            /**
             * mine : 我的这句话说的真精辟，尔等速来围观！
             * user : 我发现了一条神评论，推荐给你们膜拜下
             */

            private String mine;
            private String user;

            public String getMine() {
                return mine;
            }

            public void setMine(String mine) {
                this.mine = mine;
            }

            public String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }
        }

        public static class HotBean {
            /**
             * count : 6
             * show_time_start : 8
             * show_time_end : 24
             * gap_time : 60
             */

            private String count;
            private String show_time_start;
            private String show_time_end;
            private int gap_time;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getShow_time_start() {
                return show_time_start;
            }

            public void setShow_time_start(String show_time_start) {
                this.show_time_start = show_time_start;
            }

            public String getShow_time_end() {
                return show_time_end;
            }

            public void setShow_time_end(String show_time_end) {
                this.show_time_end = show_time_end;
            }

            public int getGap_time() {
                return gap_time;
            }

            public void setGap_time(int gap_time) {
                this.gap_time = gap_time;
            }
        }

        public static class ChatImageBean {
            /**
             * img_url :
             * h : 200
             * w : 300
             * into_type : 0
             * redirect_url :
             */

            private String img_url;
            private int h;
            private int w;
            private int into_type;
            private String redirect_url;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getInto_type() {
                return into_type;
            }

            public void setInto_type(int into_type) {
                this.into_type = into_type;
            }

            public String getRedirect_url() {
                return redirect_url;
            }

            public void setRedirect_url(String redirect_url) {
                this.redirect_url = redirect_url;
            }
        }

        public static class AdFeedbackBean {
            /**
             * no_ad_scheme : dongqiudi:///url/https://n.dongqiudi.com/webapp/freeAd.html
             * report_list : ["低俗色情","虚假诈骗","诱导点击","违法违规"]
             */

            private String no_ad_scheme;
            private List<String> report_list;

            public String getNo_ad_scheme() {
                return no_ad_scheme;
            }

            public void setNo_ad_scheme(String no_ad_scheme) {
                this.no_ad_scheme = no_ad_scheme;
            }

            public List<String> getReport_list() {
                return report_list;
            }

            public void setReport_list(List<String> report_list) {
                this.report_list = report_list;
            }
        }

        public static class TagsBean {
            /**
             * title : 默认
             * desc : 展示全部内容
             * type : 1
             * value : 0
             * is_default : 1
             * scheme : dongqiudi://ad/open_apk?gather=eyJ1cmwiOiJodHRwczpcL1wvc3RhdGljMS5kcWRnYW1lLmNvbVwvc2hhbmdsYW4tbGFzdGVzdC5hcGsiLCJkcGxfdHlwZSI6IjEiLCJkZWVwbGlua191cmwiOiJzaGFuZ2xhbjpcL1wvdjFcL21haW5cL2hvbWVcL2luZGV4In0
             * android_info : {"title":"上篮 - 懂球帝旗下篮球APP","desc":"传承我懂的专业和高品质，纯粹的篮球社区"}
             */

            private String title;
            private String desc;
            private String type;
            private String value;
            private int is_default;
            private String scheme;
            private AndroidInfoBean android_info;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public String getScheme() {
                return scheme;
            }

            public void setScheme(String scheme) {
                this.scheme = scheme;
            }

            public AndroidInfoBean getAndroid_info() {
                return android_info;
            }

            public void setAndroid_info(AndroidInfoBean android_info) {
                this.android_info = android_info;
            }

            public static class AndroidInfoBean {
                /**
                 * title : 上篮 - 懂球帝旗下篮球APP
                 * desc : 传承我懂的专业和高品质，纯粹的篮球社区
                 */

                private String title;
                private String desc;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }
            }
        }
    }

    public static class EmojiBean {
        /**
         * version : 1547709896
         * emojis : [{"id":7,"name":"懂球帝","ecount":114,"icon":"https://img1.qunliao.info/fastdfs4/M00/C8/60/ChMf8FxtD72AfRU_AAAjx46Vc2k638.png","pkg":"https://img1.qunliao.info/fastdfs5/M00/6B/A2/rB8BO2Agvj6AJTT_ADI2OeojvQ0189.zip","note":"","status":true,"deleted_at":null,"version":1612758590,"sort":1,"type":0,"scenario":0},{"id":39,"name":"下线包","ecount":3,"icon":"https://img1.qunliao.info/fastdfs4/M00/C9/85/ChMf8FzCcmqAd5lpAABFYlS1vBA590.png","pkg":"https://img1.qunliao.info/fastdfs4/M00/C9/B5/ChNLklzQ_7qAfQ2LAAC-NAv2hns278.zip","note":"","status":true,"deleted_at":null,"version":1568074879,"sort":1,"type":0,"scenario":2},{"id":13,"name":"小红单","ecount":21,"icon":"https://img1.qunliao.info/fastdfs4/M00/C8/5E/ChNLklxtD8aADUm0AAAzvsnh8IA268.png","pkg":"https://img1.qunliao.info/fastdfs5/M00/1D/3A/rB8CCl7XFp6AcNl7AARwaaGOo5g117.zip","note":"","status":true,"deleted_at":null,"version":1606495312,"sort":7,"type":0,"scenario":0}]
         * chat : []
         */

        private String version;
        private List<EmojisBean> emojis;
        private List<?> chat;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public List<EmojisBean> getEmojis() {
            return emojis;
        }

        public void setEmojis(List<EmojisBean> emojis) {
            this.emojis = emojis;
        }

        public List<?> getChat() {
            return chat;
        }

        public void setChat(List<?> chat) {
            this.chat = chat;
        }

        public static class EmojisBean {
            /**
             * id : 7
             * name : 懂球帝
             * ecount : 114
             * icon : https://img1.qunliao.info/fastdfs4/M00/C8/60/ChMf8FxtD72AfRU_AAAjx46Vc2k638.png
             * pkg : https://img1.qunliao.info/fastdfs5/M00/6B/A2/rB8BO2Agvj6AJTT_ADI2OeojvQ0189.zip
             * note :
             * status : true
             * deleted_at : null
             * version : 1612758590
             * sort : 1
             * type : 0
             * scenario : 0
             */

            private int id;
            private String name;
            private int ecount;
            private String icon;
            private String pkg;
            private String note;
            private boolean status;
            private Object deleted_at;
            private int version;
            private int sort;
            private int type;
            private int scenario;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getEcount() {
                return ecount;
            }

            public void setEcount(int ecount) {
                this.ecount = ecount;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPkg() {
                return pkg;
            }

            public void setPkg(String pkg) {
                this.pkg = pkg;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public Object getDeleted_at() {
                return deleted_at;
            }

            public void setDeleted_at(Object deleted_at) {
                this.deleted_at = deleted_at;
            }

            public int getVersion() {
                return version;
            }

            public void setVersion(int version) {
                this.version = version;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getScenario() {
                return scenario;
            }

            public void setScenario(int scenario) {
                this.scenario = scenario;
            }
        }
    }

    public static class ModulesBean {
        /**
         * id : 1545464212
         * name : 懂彩帝
         * image : https://img1.qunliao.info/fastdfs3/M00/9F/78/ChOxM1wd6W-APhe3AAAaaig4MBA829.png
         * scheme : dongqiudi:///url/https://n.dongqiudi.com/webapp/baccarat-plan.html?dqd_source=sidebar&plan_type=sporttery
         * firstTip : 0
         * icon : https://img1.qunliao.info/fastdfs5/M00/11/A0/rB8BO16W0N-AUL4DAAAGCabtGAM854.png
         * iconLittle :
         * text :
         */

        private int id;
        private String name;
        private String image;
        private String scheme;
        private int firstTip;
        private String icon;
        private String iconLittle;
        private String text;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public int getFirstTip() {
            return firstTip;
        }

        public void setFirstTip(int firstTip) {
            this.firstTip = firstTip;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIconLittle() {
            return iconLittle;
        }

        public void setIconLittle(String iconLittle) {
            this.iconLittle = iconLittle;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class TemplateBean {
        /**
         * name : all
         * url : https://static1.dongqiudi.com/dqd-node/public/news.202102051200.zip
         * md5 : 398717341c6434c0ebafb93098578ee7
         */

        private String name;
        private String url;
        private String md5;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
    }
}
