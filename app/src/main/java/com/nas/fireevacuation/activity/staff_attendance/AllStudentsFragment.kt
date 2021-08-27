package com.nas.fireevacuation.activity.staff_attendance

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.fireevacuation.R
import com.nas.fireevacuation.activity.staff_attendance.adapter.StudentAdapter
import com.nas.fireevacuation.activity.staff_home.model.students_model.Lists
import com.nas.fireevacuation.common.constants.PreferenceManager


class AllStudentsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var studentList: ArrayList<Lists>
    lateinit var a:ArrayList<Lists>
    lateinit var b:ArrayList<Lists>
    lateinit var c:ArrayList<Lists>
    lateinit var d:ArrayList<Lists>
    lateinit var e:ArrayList<Lists>
    lateinit var f:ArrayList<Lists>
    lateinit var g:ArrayList<Lists>
    lateinit var h:ArrayList<Lists>
    lateinit var i:ArrayList<Lists>
    lateinit var j:ArrayList<Lists>
    lateinit var k:ArrayList<Lists>
    lateinit var l:ArrayList<Lists>
    lateinit var m:ArrayList<Lists>
    lateinit var n:ArrayList<Lists>
    lateinit var o:ArrayList<Lists>
    lateinit var p:ArrayList<Lists>
    lateinit var q:ArrayList<Lists>
    lateinit var r:ArrayList<Lists>
    lateinit var s:ArrayList<Lists>
    lateinit var t:ArrayList<Lists>
    lateinit var u:ArrayList<Lists>
    lateinit var v:ArrayList<Lists>
    lateinit var w:ArrayList<Lists>
    lateinit var x:ArrayList<Lists>
    lateinit var y:ArrayList<Lists>
    lateinit var z:ArrayList<Lists>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_students, container, false)
        var loopVariable = 0
        recyclerView = view.findViewById(R.id.recyclerView)
        studentList = PreferenceManager.getStudentList(context!!)
        a = ArrayList()
        b = ArrayList()
        c = ArrayList()
        d = ArrayList()
        e = ArrayList()
        f = ArrayList()
        g = ArrayList()
        h = ArrayList()
        i = ArrayList()
        j = ArrayList()
        k = ArrayList()
        l = ArrayList()
        m = ArrayList()
        n = ArrayList()
        o = ArrayList()
        p = ArrayList()
        q = ArrayList()
        r = ArrayList()
        s = ArrayList()
        t = ArrayList()
        u = ArrayList()
        v = ArrayList()
        w = ArrayList()
        x = ArrayList()
        y = ArrayList()
        z = ArrayList()

        while (loopVariable<studentList.size){
            Log.e("First Letter",studentList[loopVariable].name[0].toString())
            if (studentList[loopVariable].name[0].toString().equals("A")){
                a.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("B")){
                b.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("C")){
                c.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("D")){
                d.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("E")){
                e.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("F")){
                f.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("G")){
                g.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("H")){
                h.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("I")){
                i.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("J")){
                j.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("K")){
                k.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("L")){
                l.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("M")){
                m.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("N")){
                n.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("O")){
                o.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("P")){
                p.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("Q")){
                q.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("R")){
                r.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("S")){
                s.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("T")){
                t.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("U")){
                u.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("V")){
                v.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("W")){
                w.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("X")){
                x.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("Y")){
                y.add(studentList[loopVariable])
            } else if (studentList[loopVariable].name[0].toString().equals("Z")){
                z.add(studentList[loopVariable])
            }
        loopVariable++
    }
        Log.e("A",a.toString())
        val studentAdapter = StudentAdapter(context!!, studentList)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = studentAdapter
        Log.e("Student List", studentList.toString())
        return view
    }


}