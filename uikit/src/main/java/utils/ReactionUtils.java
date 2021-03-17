package utils;

import com.cometchat.pro.uikit.Reaction.model.Reaction;

import java.util.ArrayList;
import java.util.List;

public class ReactionUtils {
    static String[] feel = {
            "😄",
            "💙",
            "😍",
            "🔥",
            "👍",
            "😈",
            "👋",
            "🙌",
            "💬",
            "😃",
            "😀",
            "😊",
            "☺",
            "😉",
            "😘",
            "😚",
            "😗",
            "😙",
            "😜",
            "😝",
            "😛",
            "😳",
            "😁",
            "😔",
            "😌",
            "😒",
            "😞",
            "😣",
            "😢",
            "😂",
            "😭",
            "😪",
            "😥",
            "😰",
            "😅",
            "😓",
            "😩",
            "😫",
            "😨",
            "😱",
            "😠",
            "😡",
            "😤",
            "😖",
            "😆",
            "😋",
            "😷",
            "😎",
            "😴",
            "😵",
            "😲",
            "😟",
            "😦",
            "😧",
            "👿",
            "😮",
            "😬",
            "😐",
            "😕",
            "😯",
            "😶",
            "😇",
            "😏",
            "😑",
            "👲",
            "👳",
            "👮",
            "👷",
            "💂",
            "👶",
            "👦",
            "👧",
            "👨",
            "👩",
            "👴",
            "👵",
            "👱",
            "👼",
            "👸",
            "😺",
            "😸",
            "😻",
            "😽",
            "😼",
            "🙀",
            "😿",
            "😹",
            "😾",
            "👹",
            "👺",
            "🙈",
            "🙉",
            "🙊",
            "💀",
            "👽",
            "💩",
            "✨",
            "🌟",
            "💫",
            "💥",
            "💢",
            "💦",
            "💧",
            "💤",
            "💨",
            "👂",
            "👀",
            "👃",
            "👅",
            "👄",
            "👎",
            "👌",
            "👊",
            "✊",
            "✌",
            "✋",
            "👐",
            "👆",
            "👇",
            "👉",
            "👈",
            "🙏",
            "☝",
            "👏",
            "💪",
            "🚶",
            "🏃",
            "💃",
            "👫",
            "👪",
            "👬",
            "👭",
            "💏",
            "💑",
            "👯",
            "🙆",
            "🙅",
            "💁",
            "🙋",
            "💆",
            "💇",
            "💅",
            "👰",
            "🙎",
            "🙍",
            "🙇",
            "🎩",
            "👑",
            "👒",
            "👟",
            "👞",
            "👡",
            "👠",
            "👢",
            "👕",
            "👔",
            "👚",
            "👗",
            "🎽",
            "👖",
            "👘",
            "👙",
            "💼",
            "👜",
            "👝",
            "👛",
            "👓",
            "🎀",
            "🌂",
            "💄",
            "💛",
            "💙",
            "💜",
            "💚",
            "❤",
            "💔",
            "💗",
            "💓",
            "💕",
            "💖",
            "💞",
            "💘",
            "💌",
            "💋",
            "💍",
            "💎",
            "👤",
            "👥",
            "👣",
            "💭"};

    static String[] flags = {
            "🇦🇨",
            "🇦🇩",
            "🇦🇪",
            "🇦🇫",
            "🇦🇬",
            "🇦🇮",
            "🇦🇱",
            "🇦🇲",
            "🇦🇴",
            "🇦🇶",
            "🇦🇷",
            "🇦🇸",
            "🇦🇹",
            "🇦🇺",
            "🇦🇼",
            "🇦🇽",
            "🇦🇿",
            "🇦",
            "🇧🇦",
            "🇧🇧",
            "🇧🇩",
            "🇧🇪",
            "🇧🇫",
            "🇧🇬",
            "🇧🇭",
            "🇧🇮",
            "🇧🇯",
            "🇧🇱",
            "🇧🇲",
            "🇧🇳",
            "🇧🇴",
            "🇧🇶",
            "🇧🇷",
            "🇧🇸",
            "🇧🇹",
            "🇧🇻",
            "🇧🇼",
            "🇧🇾",
            "🇧🇿",
            "🇧",
            "🇨🇦",
            "🇨🇨",
            "🇨🇩",
            "🇨🇫",
            "🇨🇬",
            "🇨🇭",
            "🇨🇮",
            "🇨🇰",
            "🇨🇱",
            "🇨🇲",
            "🇨🇳",
            "🇨🇴",
            "🇨🇵",
            "🇨🇷",
            "🇨🇺",
            "🇨🇻",
            "🇨🇼",
            "🇨🇽",
            "🇨🇾",
            "🇨🇿",
            "🇨",
            "🇩🇪",
            "🇩🇬",
            "🇩🇯",
            "🇩🇰",
            "🇩🇲",
            "🇩🇴",
            "🇩🇿",
            "🇩",
            "🇪🇦",
            "🇪🇨",
            "🇪🇪",
            "🇪🇬",
            "🇪🇭",
            "🇪🇷",
            "🇪🇸",
            "🇪🇹",
            "🇪🇺",
            "🇪",
            "🇫🇮",
            "🇫🇯",
            "🇫🇰",
            "🇫🇲",
            "🇫🇴",
            "🇫🇷",
            "🇫",
            "🇬🇦",
            "🇬🇧",
            "🇬🇩",
            "🇬🇪",
            "🇬🇫",
            "🇬🇬",
            "🇬🇭",
            "🇬🇮",
            "🇬🇱",
            "🇬🇲",
            "🇬🇳",
            "🇬🇵",
            "🇬🇶",
            "🇬🇷",
            "🇬🇸",
            "🇬🇹",
            "🇬🇺",
            "🇬🇼",
            "🇬🇾",
            "🇬",
            "🇭🇰",
            "🇭🇲",
            "🇭🇳",
            "🇭🇷",
            "🇭🇹",
            "🇭🇺",
            "🇭",
            "🇮🇨",
            "🇮🇩",
            "🇮🇪",
            "🇮🇱",
            "🇮🇲",
            "🇮🇳",
            "🇮🇴",
            "🇮🇶",
            "🇮🇷",
            "🇮🇸",
            "🇮🇹",
            "🇮",
            "🇯🇪",
            "🇯🇲",
            "🇯🇴",
            "🇯🇵",
            "🇯",
            "🇰🇪",
            "🇰🇬",
            "🇰🇭",
            "🇰🇮",
            "🇰🇲",
            "🇰🇳",
            "🇰🇵",
            "🇰🇷",
            "🇰🇼",
            "🇰🇾",
            "🇰🇿",
            "🇰",
            "🇱🇦",
            "🇱🇧",
            "🇱🇨",
            "🇱🇮",
            "🇱🇰",
            "🇱🇷",
            "🇱🇸",
            "🇱🇹",
            "🇱🇺",
            "🇱🇻",
            "🇱🇾",
            "🇱",
            "🇲🇦",
            "🇲🇨",
            "🇲🇩",
            "🇲🇪",
            "🇲🇫",
            "🇲🇬",
            "🇲🇭",
            "🇲🇰",
            "🇲🇱",
            "🇲🇲",
            "🇲🇳",
            "🇲🇴",
            "🇲🇵",
            "🇲🇶",
            "🇲🇷",
            "🇲🇸",
            "🇲🇹",
            "🇲🇺",
            "🇲🇻",
            "🇲🇼",
            "🇲🇽",
            "🇲🇾",
            "🇲🇿",
            "🇲",
            "🇳🇦",
            "🇳🇨",
            "🇳🇪",
            "🇳🇫",
            "🇳🇬",
            "🇳🇮",
            "🇳🇱",
            "🇳🇴",
            "🇳🇵",
            "🇳🇷",
            "🇳🇺",
            "🇳🇿",
            "🇳",
            "🇴🇲",
            "🇴",
            "🇵🇦",
            "🇵🇪",
            "🇵🇫",
            "🇵🇬",
            "🇵🇭",
            "🇵🇰",
            "🇵🇱",
            "🇵🇲",
            "🇵🇳",
            "🇵🇷",
            "🇵🇸",
            "🇵🇹",
            "🇵🇼",
            "🇵🇾",
            "🇵",
            "🇶🇦",
            "🇶",
            "🇷🇪",
            "🇷🇴",
            "🇷🇸",
            "🇷🇺",
            "🇷🇼",
            "🇷",
            "🇸🇦",
            "🇸🇧",
            "🇸🇨",
            "🇸🇩",
            "🇸🇪",
            "🇸🇬",
            "🇸🇭",
            "🇸🇮",
            "🇸🇯",
            "🇸🇰",
            "🇸🇱",
            "🇸🇲",
            "🇸🇳",
            "🇸🇴",
            "🇸🇷",
            "🇸🇸",
            "🇸🇹",
            "🇸🇻",
            "🇸🇽",
            "🇸🇾",
            "🇸🇿",
            "🇸",
            "🇹🇦",
            "🇹🇨",
            "🇹🇩",
            "🇹🇫",
            "🇹🇬",
            "🇹🇭",
            "🇹🇯",
            "🇹🇰",
            "🇹🇱",
            "🇹🇲",
            "🇹🇳",
            "🇹🇴",
            "🇹🇷",
            "🇹🇹",
            "🇹🇻",
            "🇹🇼",
            "🇹🇿",
            "🇹",
            "🇺🇦",
            "🇺🇬",
            "🇺🇲",
            "🇺🇳",
            "🇺🇸",
            "🇺🇾",
            "🇺🇿",
            "🇺",
            "🇻🇦",
            "🇻🇨",
            "🇻🇪",
            "🇻🇬",
            "🇻🇮",
            "🇻🇳",
            "🇻🇺",
            "🇻",
            "🇼🇫",
            "🇼🇸",
            "🇼",
            "🇽🇰",
            "🇽",
            "🇾🇪",
            "🇾🇹",
            "🇾",
            "🇿🇦",
            "🇿🇲"
    };

    public static final int[] PEOPLE = {
            0x1f604,
            0x1f603,
            0x1f600,
            0x1f60a,
            0x1f609,
            0x1f60d,
            0x1f618,
            0x1f61a,
            0x1f617,
            0x1f619,
            0x1f61c,
            0x1f61d,
            0x1f61b,
            0x1f633,
            0x1f601,
            0x1f614,
            0x1f60c,
            0x1f612,
            0x1f61e,
            0x1f623,
            0x1f622,
            0x1f602,
            0x1f62d,
            0x1f62a,
            0x1f625,
            0x1f630,
            0x1f605,
            0x1f613,
            0x1f629,
            0x1f62b,
            0x1f628,
            0x1f631,
            0x1f620,
            0x1f621,
            0x1f624,
            0x1f616,
            0x1f606,
            0x1f60b,
            0x1f637,
            0x1f60e,
            0x1f634,
            0x1f635,
            0x1f632,
            0x1f61f,
            0x1f626,
            0x1f627,
            0x1f608,
            0x1f47f,
            0x1f62e,
            0x1f62c,
            0x1f610,
            0x1f615,
            0x1f62f,
            0x1f636,
            0x1f607,
            0x1f60f,
            0x1f611,
            0x1f472,
            0x1f473,
            0x1f46e,
            0x1f477,
            0x1f482,
            0x1f476,
            0x1f466,
            0x1f467,
            0x1f468,
            0x1f469,
            0x1f474,
            0x1f475,
            0x1f471,
            0x1f47c,
            0x1f478,
            0x1f63a,
            0x1f638,
            0x1f63b,
            0x1f63d,
            0x1f63c,
            0x1f640,
            0x1f63f,
            0x1f639,
            0x1f63e,
            0x1f479,
            0x1f47a,
            0x1f648,
            0x1f649,
            0x1f64a,
            0x1f480,
            0x1f47d,
            0x1f4a9,
            0x1f525,
            0x1f31f,
            0x1f4ab,
            0x1f4a5,
            0x1f4a2,
            0x1f4a6,
            0x1f4a7,
            0x1f4a4,
            0x1f4a8,
            0x1f442,
            0x1f440,
            0x1f443,
            0x1f445,
            0x1f444,
            0x1f44d,
            0x1f44e,
            0x1f44c,
            0x1f44a,
            0x1f44b,
            0x1f450,
            0x1f446,
            0x1f447,
            0x1f449,
            0x1f448,
            0x1f64c,
            0x1f64f,
            0x1f44f,
            0x1f4aa,
            0x1f6b6,
            0x1f3c3,
            0x1f483,
            0x1f46b,
            0x1f46a,
            0x1f46c,
            0x1f46d,
            0x1f48f,
            0x1f491,
            0x1f46f,
            0x1f646,
            0x1f645,
            0x1f481,
            0x1f64b,
            0x1f486,
            0x1f487,
            0x1f485,
            0x1f470,
            0x1f64e,
            0x1f64d,
            0x1f647,
            0x1f3a9,
            0x1f451,
            0x1f452,
            0x1f45f,
            0x1f45e,
            0x1f461,
            0x1f460,
            0x1f462,
            0x1f455,
            0x1f454,
            0x1f45a,
            0x1f457,
            0x1f3bd,
            0x1f456,
            0x1f458,
            0x1f459,
            0x1f4bc,
            0x1f45c,
            0x1f45d,
            0x1f45b,
            0x1f453,
            0x1f380,
            0x1f302,
            0x1f484,
            0x1f49b,
            0x1f499,
            0x1f49c,
            0x1f49a,
            0x1f494,
            0x1f497,
            0x1f493,
            0x1f495,
            0x1f496,
            0x1f49e,
            0x1f498,
            0x1f48c,
            0x1f48b,
            0x1f48d,
            0x1f48e,
            0x1f464,
            0x1f465,
            0x1f4ac,
            0x1f463,
            0x1f4ad,
    };

    public static final int[] NATURE = {
            0x1f436,
            0x1f43a,
            0x1f431,
            0x1f42d,
            0x1f439,
            0x1f430,
            0x1f438,
            0x1f42f,
            0x1f428,
            0x1f43b,
            0x1f437,
            0x1f43d,
            0x1f42e,
            0x1f417,
            0x1f435,
            0x1f412,
            0x1f434,
            0x1f411,
            0x1f418,
            0x1f43c,
            0x1f427,
            0x1f426,
            0x1f424,
            0x1f425,
            0x1f423,
            0x1f414,
            0x1f40d,
            0x1f422,
            0x1f41b,
            0x1f41d,
            0x1f41c,
            0x1f41e,
            0x1f40c,
            0x1f419,
            0x1f41a,
            0x1f420,
            0x1f41f,
            0x1f42c,
            0x1f433,
            0x1f40b,
            0x1f404,
            0x1f40f,
            0x1f400,
            0x1f403,
            0x1f405,
            0x1f407,
            0x1f409,
            0x1f40e,
            0x1f410,
            0x1f413,
            0x1f415,
            0x1f416,
            0x1f401,
            0x1f402,
            0x1f432,
            0x1f421,
            0x1f40a,
            0x1f42b,
            0x1f42a,
            0x1f406,
            0x1f408,
            0x1f429,
            0x1f43e,
            0x1f490,
            0x1f338,
            0x1f337,
            0x1f340,
            0x1f339,
            0x1f33b,
            0x1f33a,
            0x1f341,
            0x1f343,
            0x1f342,
            0x1f33f,
            0x1f33e,
            0x1f344,
            0x1f335,
            0x1f334,
            0x1f332,
            0x1f333,
            0x1f330,
            0x1f331,
            0x1f33c,
            0x1f310,
            0x1f31e,
            0x1f31d,
            0x1f31a,
            0x1f311,
            0x1f312,
            0x1f313,
            0x1f314,
            0x1f315,
            0x1f316,
            0x1f317,
            0x1f318,
            0x1f31c,
            0x1f31b,
            0x1f319,
            0x1f30d,
            0x1f30e,
            0x1f30f,
            0x1f30b,
            0x1f30c,
            0x1f320,
            0x1f300,
            0x1f301,
            0x1f308,
            0x1f30a
    };

    public static final int[] PLACES = {
            0x1f3e0,
            0x1f3e1,
            0x1f3eb,
            0x1f3e2,
            0x1f3e3,
            0x1f3e5,
            0x1f3e6,
            0x1f3ea,
            0x1f3e9,
            0x1f3e8,
            0x1f492,
            0x1f3ec,
            0x1f3e4,
            0x1f307,
            0x1f306,
            0x1f3ef,
            0x1f3f0,
            0x1f3ed,
            0x1f5fc,
            0x1f5fe,
            0x1f5fb,
            0x1f304,
            0x1f305,
            0x1f303,
            0x1f5fd,
            0x1f309,
            0x1f3a0,
            0x1f3a1,
            0x1f3a2,
            0x1f6a2,
            0x1f6a4,
            0x1f6a3,
            0x1f680,
            0x1f4ba,
            0x1f681,
            0x1f682,
            0x1f68a,
            0x1f689,
            0x1f69e,
            0x1f686,
            0x1f684,
            0x1f685,
            0x1f688,
            0x1f687,
            0x1f69d,
            0x1f68b,
            0x1f683,
            0x1f68e,
            0x1f68c,
            0x1f68d,
            0x1f699,
            0x1f698,
            0x1f697,
            0x1f695,
            0x1f696,
            0x1f69b,
            0x1f69a,
            0x1f6a8,
            0x1f693,
            0x1f694,
            0x1f692,
            0x1f691,
            0x1f690,
            0x1f6b2,
            0x1f6a1,
            0x1f69f,
            0x1f6a0,
            0x1f69c,
            0x1f488,
            0x1f68f,
            0x1f3ab,
            0x1f6a6,
            0x1f6a5,
            0x1f6a7,
            0x1f530,
            0x1f3ee,
            0x1f3b0,
            0x1f5ff,
            0x1f3aa,
            0x1f3ad,
            0x1f4cd,
            0x1f6a9
    };

    public static final int[] OBJECT = {
            0x1f38d,
            0x1f49d,
            0x1f38e,
            0x1f392,
            0x1f393,
            0x1f38f,
            0x1f386,
            0x1f387,
            0x1f390,
            0x1f391,
            0x1f383,
            0x1f47b,
            0x1f385,
            0x1f384,
            0x1f381,
            0x1f38b,
            0x1f389,
            0x1f38a,
            0x1f388,
            0x1f38c,
            0x1f52e,
            0x1f3a5,
            0x1f4f7,
            0x1f4f9,
            0x1f4fc,
            0x1f4bf,
            0x1f4c0,
            0x1f4bd,
            0x1f4be,
            0x1f4bb,
            0x1f4f1,
            0x1f4de,
            0x1f4df,
            0x1f4e0,
            0x1f4e1,
            0x1f4fa,
            0x1f4fb,
            0x1f508,
            0x1f509,
            0x1f50a,
            0x1f507,
            0x1f514,
            0x1f515,
            0x1f4e2,
            0x1f4e3,
            0x1f513,
            0x1f512,
            0x1f50f,
            0x1f510,
            0x1f511,
            0x1f50e,
            0x1f4a1,
            0x1f526,
            0x1f506,
            0x1f505,
            0x1f50c,
            0x1f50b,
            0x1f50d,
            0x1f6c1,
            0x1f6c0,
            0x1f6bf,
            0x1f6bd,
            0x1f527,
            0x1f529,
            0x1f528,
            0x1f6aa,
            0x1f6ac,
            0x1f4a3,
            0x1f52b,
            0x1f52a,
            0x1f48a,
            0x1f489,
            0x1f4b0,
            0x1f4b4,
            0x1f4b5,
            0x1f4b7,
            0x1f4b6,
            0x1f4b3,
            0x1f4b8,
            0x1f4f2,
            0x1f4e7,
            0x1f4e5,
            0x1f4e4,
            0x1f4e9,
            0x1f4e8,
            0x1f4ef,
            0x1f4eb,
            0x1f4ea,
            0x1f4ec,
            0x1f4ed,
            0x1f4ee,
            0x1f4e6,
            0x1f4dd,
            0x1f4c4,
            0x1f4c3,
            0x1f4d1,
            0x1f4ca,
            0x1f4c8,
            0x1f4c9,
            0x1f4dc,
            0x1f4cb,
            0x1f4c5,
            0x1f4c6,
            0x1f4c7,
            0x1f4c1,
            0x1f4c2,
            0x1f4cc,
            0x1f4ce,
            0x1f4cf,
            0x1f4d0,
            0x1f4d5,
            0x1f4d7,
            0x1f4d8,
            0x1f4d9,
            0x1f4d3,
            0x1f4d4,
            0x1f4d2,
            0x1f4da,
            0x1f4d6,
            0x1f516,
            0x1f4db,
            0x1f52c,
            0x1f52d,
            0x1f4f0,
            0x1f3a8,
            0x1f3ac,
            0x1f3a4,
            0x1f3a7,
            0x1f3bc,
            0x1f3b5,
            0x1f3b6,
            0x1f3b9,
            0x1f3bb,
            0x1f3ba,
            0x1f3b7,
            0x1f3b8,
            0x1f47e,
            0x1f3ae,
            0x1f0cf,
            0x1f3b4,
            0x1f004,
            0x1f3b2,
            0x1f3af,
            0x1f3c8,
            0x1f3c0,
            0x1f3be,
            0x1f3b1,
            0x1f3c9,
            0x1f3b3,
            0x1f6b5,
            0x1f6b4,
            0x1f3c1,
            0x1f3c7,
            0x1f3c6,
            0x1f3bf,
            0x1f3c2,
            0x1f3ca,
            0x1f3c4,
            0x1f3a3,
            0x1f375,
            0x1f376,
            0x1f37c,
            0x1f37a,
            0x1f37b,
            0x1f378,
            0x1f379,
            0x1f377,
            0x1f374,
            0x1f355,
            0x1f354,
            0x1f35f,
            0x1f357,
            0x1f356,
            0x1f35d,
            0x1f35b,
            0x1f364,
            0x1f371,
            0x1f363,
            0x1f365,
            0x1f359,
            0x1f358,
            0x1f35a,
            0x1f35c,
            0x1f372,
            0x1f362,
            0x1f361,
            0x1f373,
            0x1f35e,
            0x1f369,
            0x1f36e,
            0x1f366,
            0x1f368,
            0x1f367,
            0x1f382,
            0x1f370,
            0x1f36a,
            0x1f36b,
            0x1f36c,
            0x1f36d,
            0x1f36f,
            0x1f34e,
            0x1f34f,
            0x1f34a,
            0x1f34b,
            0x1f352,
            0x1f347,
            0x1f349,
            0x1f353,
            0x1f351,
            0x1f348,
            0x1f34c,
            0x1f350,
            0x1f34d,
            0x1f360,
            0x1f346,
            0x1f345,
            0x1f33d
    };
    public static Reaction[] getPeopleList() {
        Reaction[] result = new Reaction[PEOPLE.length];
        for (int i=0;i<PEOPLE.length;i++) {
            result[i] = new Reaction(new String(Character.toChars(PEOPLE[i])),PEOPLE[i]);
        }
        return result;
    }
    public static Reaction[] getNatureList() {
        Reaction[] result = new Reaction[NATURE.length];
        for (int i=0;i<NATURE.length;i++) {
            result[i] = new Reaction(new String(Character.toChars(NATURE[i])),NATURE[i]);
        }
        return result;
    }
    public static Reaction[] getPlacesList() {
        Reaction[] result = new Reaction[PLACES.length];
        for (int i=0;i<PLACES.length;i++) {
            result[i] = new Reaction(new String(Character.toChars(PLACES[i])),PLACES[i]);
        }
        return result;
    }
    public static Reaction[] getObjectList() {
        Reaction[] result = new Reaction[OBJECT.length];
        for (int i=0;i<OBJECT.length;i++) {
            result[i] = new Reaction(new String(Character.toChars(OBJECT[i])),OBJECT[i]);
        }
        return result;
    }
    public static List<Reaction> getFeelList() {
        List<Reaction> resultList = new ArrayList<>();
        for (int i=0;i<feel.length;i++) {
            Reaction reaction = new Reaction(feel[i],i);
            resultList.add(reaction);
        }
        return resultList;
    }

    public static List<Reaction> getFlagList() {
        List<Reaction> resultList = new ArrayList<>();
        for (int i=0;i<flags.length;i++) {
//            Reaction reaction = new Reaction("flag "+i,flags[i]);
//            resultList.add(reaction);
        }return resultList;
    }
}